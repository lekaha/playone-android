package com.playone.mobile.ui.create

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.playone.mobile.ui.BaseFragment
import com.playone.mobile.ui.R
import kotlinx.android.synthetic.main.fragment_playone_create_select_map.map_select_location

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback {

    override fun getLayoutId() = R.layout.fragment_playone_create_select_map

    companion object {
        const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"

        fun newInstance() = SelectLocationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }

        map_select_location.onCreate(mapViewBundle)
        map_select_location.getMapAsync(this)
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

    override fun onMapReady(p0: GoogleMap?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}