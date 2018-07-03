package com.playone.mobile.ui.model

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.location.Geocoder
import android.location.Location
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
import java.io.IOException
import java.util.Date

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
    }

    var locationPermissionGranted: MutableLiveData<Boolean> = MutableLiveData()
    var currentLatLng: MutableLiveData<LatLng> = MutableLiveData()
    var currentAddress: MutableLiveData<String> = MutableLiveData()
    var currentNearby: MutableLiveData<List<AutocompletePrediction>> = MutableLiveData()
    var isPlayoneCreated: MutableLiveData<Boolean> = MutableLiveData()

    private var map: GoogleMap? = null
    private var cameraZoom: Float = DEFAULT_ZOOM_IN

    init {
        presenter.setView(this)
        currentLatLng.value = LatLng(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LNG)
    }

    override fun setPresenter(presenter: CreatePlayoneContract.Presenter) {
        this.presenter = presenter
        this.presenter.setView(this)
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
            currentLatLng.value!!, { predictions ->
                currentNearby.value = predictions
            }
        ).execute(keyword)
    }

    fun movePlaceByKeyword(keyword: String) {

        AsyncPredictPlaces(
            geoDataClient,
            autocompleteFilter,
            // currentLatLng is must existed
            currentLatLng.value!!, { predictions ->
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
        ).execute(keyword)
    }

    fun createPlayone(createPlayoneParameters: CreatePlayoneContract.CreatePlayoneParameters) {
        presenter.create(createPlayoneParameters)
    }

    fun createPlayone(
        name: String,
        description: String,
        playoneDate: Date,
        latitude: Double,
        longtitude: Double,
        address: String,
        limitPeople: Int,
        level: Int
    ) {
        createPlayone(CreatePlayoneContract.CreatePlayoneParameters(
            name,
            description,
            playoneDate,
            CreatePlayoneContract.PlayonePlace(longtitude, latitude, address),
            limitPeople,
            level
        ))
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