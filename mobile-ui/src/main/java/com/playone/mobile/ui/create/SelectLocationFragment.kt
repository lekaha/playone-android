package com.playone.mobile.ui.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.gms.location.places.AutocompletePrediction
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.orString
import com.playone.mobile.ui.BaseFragment
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.create.NearbyPlacesAdapter.Companion.TYPE_NEAR_BY_PLACES
import com.playone.mobile.ui.model.CreatePlayoneViewModel
import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory
import kotlinx.android.synthetic.main.fragment_playone_create_select_map.actv_add_loc
import kotlinx.android.synthetic.main.fragment_playone_create_select_map.rv_result_places
import kotlinx.android.synthetic.main.merge_address_map.layout_current_location
import kotlinx.android.synthetic.main.merge_address_map.map_select_location
import kotlinx.android.synthetic.main.merge_address_map.txt_current_addr
import javax.inject.Inject

class SelectLocationFragment : BaseFragment() {

    companion object {

        const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        const val PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0x301
        fun newInstance() = SelectLocationFragment()
    }

    @Inject lateinit var autocompleteAdapter: GooglePlacesAutocompleteAdapter
    @Inject lateinit var navigator: Navigator

    private lateinit var viewModel: CreatePlayoneViewModel
    private lateinit var nearByAdapter: NearbyPlacesAdapter

    override fun getLayoutId() = R.layout.fragment_playone_create_select_map

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        appCompatActivity?.let { activity ->

            viewModel =
                ViewModelProviders.of(activity).get(CreatePlayoneViewModel::class.java).apply {

                    locationPermissionGranted.observe(this@SelectLocationFragment, Observer {
                        it.ifTrue {
                            // TODO update the map to current location
                        }
                    })

                    currentLatLng.observe(this@SelectLocationFragment, Observer {
                        it?.let {
                            autocompleteAdapter.currentLatLng = it
                            searchNearbyPlaces(actv_add_loc.text.toString().orString("park"))
                        }
                    })

                    currentAddress.observe(this@SelectLocationFragment, Observer {
                        txt_current_addr.text = it
                    })

                    currentNearby.observe(this@SelectLocationFragment, Observer {
                        it?.let {
                            nearByAdapter.update(it.map {
                                DisplayableItem.toDisplayableItem(it, TYPE_NEAR_BY_PLACES, {
                                    val prediction = AutocompletePrediction::class.java.cast(it)
                                    viewModel.movePlaceByKeyword(prediction.getPrimaryText(null).toString())
                                })
                            })
                            nearByAdapter.notifyDataSetChanged()
                        }

                    })

                    map_select_location.onCreate(savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY))
                    map_select_location.getMapAsync(this)

                    lifecycle::addObserver
                }

            getLocationPermission()
            setupViews()
        }
    }

    private fun setupViews() {

        actv_add_loc.setAdapter(autocompleteAdapter)
        actv_add_loc.setOnItemClickListener { _, view, _, _ ->
            viewModel.movePlaceByKeyword((view as TextView).text.toString())
            closeSoftKeyboard()
        }

        nearByAdapter = NearbyPlacesAdapter(
            NearbyPlacesAdapter.NearbyPlaceItemComparator(),
            mapOf(Pair<Int, ViewHolderFactory>(TYPE_NEAR_BY_PLACES,
                                               NearbyPlacesViewHolder.NearbyPlacesViewHolderFactory(
                                                   appCompatActivity!!))),
            mapOf(Pair<Int, ViewHolderBinder>(TYPE_NEAR_BY_PLACES,
                                              NearbyPlacesViewHolder.NearbyPlacesViewHolderBinder()))
        )
        rv_result_places.layoutManager = LinearLayoutManager(appCompatActivity)
        rv_result_places.adapter = nearByAdapter

        layout_current_location.setOnClickListener {
            viewModel.currentLatLng.value?.let { current ->
                appCompatActivity?.let {
                    navigator.navigateToFragment(it) {
                        add(R.id.fragment_content, CreatePlayoneFragment.newInstance(
                            current.latitude,
                            current.longitude,
                            viewModel.currentAddress.value.orEmpty()
                        ))
                        hide(this@SelectLocationFragment)
                        addToBackStack(null)
                    }
                }
            }
        }
    }

    private fun closeSoftKeyboard() {

        view?.let { v ->
            appCompatActivity?.let {
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }

        map_select_location.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {

        super.onResume()
        map_select_location.onResume()
    }

    override fun onStop() {

        super.onStop()
        map_select_location.onStop()
    }

    override fun onPause() {

        super.onPause()
        map_select_location.onPause()
    }

    override fun onLowMemory() {

        super.onLowMemory()
        map_select_location.onLowMemory()
    }

    private fun getLocationPermission() {

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        appCompatActivity?.let {
            if (ContextCompat.checkSelfPermission(it,
                                                  android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                viewModel.setLocationPermissionGranted(true)
            }
            else {
                ActivityCompat.requestPermissions(it,
                                                  arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                                                  PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        viewModel.setLocationPermissionGranted(false)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults.any { it == PackageManager.PERMISSION_GRANTED }) {
                    viewModel.setLocationPermissionGranted(true)
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}