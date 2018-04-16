package com.playone.mobile.ui.create

import android.os.AsyncTask
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.maps.model.LatLng
import com.playone.mobile.common.LatLngUtils
import java.util.ArrayList

class AsyncPredictPlaces(
    private val geoDataClient: GeoDataClient,
    private val autocompleteFilter: AutocompleteFilter,
    private val currentLatLng: LatLng,
    private val update: (ArrayList<AutocompletePrediction>) -> Unit = {}
) : AsyncTask<String, Void, ArrayList<AutocompletePrediction>>() {

    @Synchronized
    override fun doInBackground(vararg constraint: String): ArrayList<AutocompletePrediction> {
        val result = mutableListOf<AutocompletePrediction>()
        var running = true
        constraint.takeIf { it.isNotEmpty() }?.let {
            geoDataClient.getAutocompletePredictions(
                it[0],
                LatLngUtils.createBoundsWithRectangle(currentLatLng, 1000.0),
                autocompleteFilter
            )
                .addOnSuccessListener { responses ->
                    responses.forEach {
                        result.add(it)
                    }
                    running = false
                }
                .addOnFailureListener {
                    running = false
                }
        }

        while (running) {
            Thread.sleep(10)
        }

        return ArrayList(result)
    }

    override fun onPostExecute(result: ArrayList<AutocompletePrediction>) {
        update(result)
    }
}