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
import com.playone.mobile.ui.create.AsyncPredictPlaces

class CreatePlayoneViewModel(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val geoDataClient: GeoDataClient,
    private val geocoder: Geocoder,
    private val autocompleteFilter: AutocompleteFilter
) : BaseViewModel(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

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

    private var map: GoogleMap? = null

    init {

        currentLatLng.value = LatLng(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LNG)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        locationPermissionGranted.value.ifTrue {
            setUpMap()
        }
    }

    override fun onCameraIdle() {

        map?.let {
            currentLatLng.value = it.cameraPosition.target
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
                    moveCamera(CameraUpdateFactory.newLatLngZoom(
                        currentLatLng.value,
                        DEFAULT_ZOOM_IN
                    ))
                } ?: run {
                    val locationRequest = LocationRequest.create()
                    locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

                    moveCamera(CameraUpdateFactory.newLatLngZoom(
                        currentLatLng.value,
                        DEFAULT_ZOOM_IN
                    ))

                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {

                                locationResult ?: return

                                val location = locationResult.lastLocation
                                currentLatLng.value = LatLng(location.latitude, location.longitude)
                                moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng.value,
                                    DEFAULT_ZOOM_IN
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
                            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                it[0].latLng,
                                DEFAULT_ZOOM_IN
                            ))
                        }
                    }

                }
            }
        ).execute(keyword)
    }

    class CreatePlayoneViewModelFactory(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val geoDataClient: GeoDataClient,
        private val geocoder: Geocoder,
        private val autocompleteFilter: AutocompleteFilter
        ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(CreatePlayoneViewModel::class.java)) {

                return CreatePlayoneViewModel(fusedLocationProviderClient,
                                              geoDataClient,
                                              geocoder,
                                              autocompleteFilter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }
    }
}