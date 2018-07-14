package com.playone.mobile.ui.model

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.createPlayone.CreatePlayoneContract
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.create.AsyncPredictPlaces
import com.playone.mobile.ui.ext.observe
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePlayoneViewModel(
    private var presenter: CreatePlayoneContract.Presenter,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val geoDataClient: GeoDataClient,
    private val geocoder: Geocoder,
    private val autocompleteFilter: AutocompleteFilter
) : BaseViewModel(),
    OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener,
    CreatePlayoneContract.View {

    companion object {

        const val DEFAULT_ZOOM_IN: Float = 11f
        const val MIN_ZOOM: Float = 4f
        const val MAX_ZOOM: Float = 15f
        const val DEFAULT_LOCATION_LAT: Double = 35.6895
        const val DEFAULT_LOCATION_LNG: Double = 139.6917

        const val MSG_FINISH_SAVED_TMP_FILE = 0x11
    }

    var locationPermissionGranted: MutableLiveData<Boolean> = MutableLiveData()
    var currentLatLng: MutableLiveData<LatLng> = MutableLiveData()
    var currentAddress: MutableLiveData<String> = MutableLiveData()
    var currentNearby: MutableLiveData<List<AutocompletePrediction>> = MutableLiveData()
    var isPlayoneCreated: MutableLiveData<Boolean> = MutableLiveData()
    var playoneCoverImage: MutableLiveData<Bitmap> = MutableLiveData()

    private var map: GoogleMap? = null
    private var cameraZoom: Float = DEFAULT_ZOOM_IN

    private val bgHandlerThread = HandlerThread("CreatePlayoneViewModelBgThread")
    private val bgHandler by lazy {
        bgHandlerThread.start()
        Handler(bgHandlerThread.looper)
    }

    private val fgHandler by lazy {
        Handler(Looper.getMainLooper()) {
            when (it.what) {
                MSG_FINISH_SAVED_TMP_FILE -> {
                    if (it.obj is CreatePlayoneContract.CreatePlayoneParameters) {
                        val playone = it.obj as CreatePlayoneContract.CreatePlayoneParameters
                        presenter.create(playone)
                    }
                     true
                }
                else -> {
                    true
                }
            }
        }
    }

    init {
        presenter.setView(this)
        currentLatLng.value = LatLng(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LNG)
    }

    override fun setPresenter(presenter: CreatePlayoneContract.Presenter) {
        this.presenter = presenter
        this.presenter.setView(this)
    }

    override fun onCleared() {
        super.onCleared()
        bgHandlerThread.looper?.quit()
    }

    override fun onPlayoneCreated(playone: PlayoneView) {
        isPlayoneCreated.value = true
    }

    override fun onResponse(response: ViewResponse<PlayoneView>) {
        when(response.status) {
            ViewResponse.Status.LOADING -> { isProgressing.value = true }
            ViewResponse.Status.ERROR -> {
                isProgressing.value = false
                occurredError.value = response.error
            }
            ViewResponse.Status.SUCCESS -> {
                isProgressing.value = false
                response.data?.let(::onPlayoneCreated)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        locationPermissionGranted.value.ifTrue {
            setUpMap()
        }
    }

    override fun onCameraIdle() {

        map?.let {
            cameraZoom = it.cameraPosition.zoom
            currentLatLng.value = it.cameraPosition.target
            try {
                val result = geocoder.getFromLocation(it.cameraPosition.target.latitude,
                                                      it.cameraPosition.target.longitude, 1)
                currentAddress.value = result.takeIf { it.isNotEmpty() }?.let {
                    val stringBuilder = StringBuilder()
                    for (index in 0..it[0].maxAddressLineIndex) {
                        stringBuilder.append(it[0].getAddressLine(index))
                        if (it[0].maxAddressLineIndex > index) {
                            stringBuilder.append(", ")
                        }
                    }

                    stringBuilder.toString()
                } ?: ""
            }
            catch (exception: IOException) {
                currentAddress.value = null
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpMap() {

        map?.apply {
            isMyLocationEnabled = true
            setMinZoomPreference(MIN_ZOOM)
            setMaxZoomPreference(MAX_ZOOM)
            uiSettings.isMyLocationButtonEnabled = true
            uiSettings.isZoomGesturesEnabled = true
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { it: Location? ->
                it?.let {
                    currentLatLng.value = LatLng(it.latitude, it.longitude)
                    animateCamera(CameraUpdateFactory.newLatLngZoom(
                        currentLatLng.value,
                        cameraZoom
                    ))
                } ?: run {
                    val locationRequest = LocationRequest.create()
                    locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

                    animateCamera(CameraUpdateFactory.newLatLngZoom(
                        currentLatLng.value,
                        cameraZoom
                    ))

                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {

                                locationResult ?: return

                                val location = locationResult.lastLocation
                                currentLatLng.value = LatLng(location.latitude, location.longitude)
                                animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng.value,
                                    cameraZoom
                                ))

                                fusedLocationProviderClient.removeLocationUpdates(this)
                            }
                        },
                        null
                    )
                }
            }

            setOnCameraIdleListener(this@CreatePlayoneViewModel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun tearDownMap() {

        map?.apply {
            isMyLocationEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
        }
    }

    @SuppressLint("MissingPermission")
    fun setLocationPermissionGranted(isGranted: Boolean) {

        locationPermissionGranted.value = isGranted

        isGranted.ifTrue {
            setUpMap()
        } otherwise {
            tearDownMap()
        }
    }

    fun searchNearbyPlaces(keyword: String) {

        AsyncPredictPlaces(
            geoDataClient,
            autocompleteFilter,
            // currentLatLng is must existed
            currentLatLng.value!!) { predictions ->
                currentNearby.value = predictions
            }
        .execute(keyword)
    }

    fun movePlaceByKeyword(keyword: String) {

        AsyncPredictPlaces(
            geoDataClient,
            autocompleteFilter,
            // currentLatLng is must existed
            currentLatLng.value!!) { predictions ->
                predictions.takeIf { it.isNotEmpty() }?.let {
                    geoDataClient.getPlaceById(it[0].placeId).addOnSuccessListener {

                        if (it.count > 0) {
                            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                it[0].latLng,
                                cameraZoom
                            ))
                        }
                    }

                }
            }
        .execute(keyword)
    }

    fun createPlayone(
        createPlayoneParameters: CreatePlayoneContract.CreatePlayoneParameters,
        destination: File
    ) {
        isProgressing.value = true
        bgHandler.post {
            createPlayoneParameters.coverImagePath = saveTempImage(destination)
            val msg = fgHandler.obtainMessage(MSG_FINISH_SAVED_TMP_FILE, createPlayoneParameters)
            fgHandler.sendMessage(msg)
        }
    }

    fun setPlayoneCoverImage(bitmap: Bitmap) {
        playoneCoverImage.value = bitmap
    }

    fun observePlayoneCoverImage(owner: LifecycleOwner, body:(v: Bitmap?) -> Unit) {
        playoneCoverImage.observe(owner, body)
    }

    private fun writeBitmapToFile(path: String, bitmap: Bitmap) {
        val fos = FileOutputStream(File(path))
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    }

    private fun saveTempImage(destination: File) = playoneCoverImage.value?.run {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                                         Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"

        val path = File.createTempFile(
            imageFileName,
            ".jpg",
            destination
        ).absolutePath

        writeBitmapToFile(path, this)
        path
    } ?: ""

    fun createPlayone(
        name: String,
        description: String,
        playoneDate: Date,
        latitude: Double,
        longtitude: Double,
        address: String,
        limitPeople: Int,
        level: Int,
        destination: File
    ) {
        createPlayone(CreatePlayoneContract.CreatePlayoneParameters(
            name,
            description,
            playoneDate,
            CreatePlayoneContract.PlayonePlace(longtitude, latitude, address),
            limitPeople,
            level
        ), destination)
    }

    class CreatePlayoneViewModelFactory(
        private val presenter: CreatePlayoneContract.Presenter,
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val geoDataClient: GeoDataClient,
        private val geocoder: Geocoder,
        private val autocompleteFilter: AutocompleteFilter
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(CreatePlayoneViewModel::class.java)) {

                return CreatePlayoneViewModel(presenter,
                                              fusedLocationProviderClient,
                                              geoDataClient,
                                              geocoder,
                                              autocompleteFilter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }
    }
}