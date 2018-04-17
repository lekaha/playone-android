package com.playone.mobile.ui.create

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.maps.model.LatLng
import java.util.ArrayList

class GooglePlacesAutocompleteAdapter(
    private val geoDataClient: GeoDataClient,
    private val autocompleteFilter: AutocompleteFilter,
    context: Context,
    textViewResourceId: Int
) :
    ArrayAdapter<String>(context, textViewResourceId), Filterable {

    private var resultList: ArrayList<String> = ArrayList()

    var currentLatLng: LatLng? = null

    override fun getCount() = resultList.size
    override fun getItem(index: Int) = resultList[index]

    override fun getFilter() =
        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                // FIXME: Nothing will happen when users input the first word. * It might be not a bug.
                if (constraint != null) {
                    // Retrieve the placeAutoComplete results.
                    val predictions = AsyncPredictPlaces(
                        geoDataClient,
                        autocompleteFilter,
                        currentLatLng!!
                    ).execute(constraint.toString()).get()

                    resultList.clear()
                    predictions.forEach {
                        resultList.add(it.getPrimaryText(null).toString())
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = resultList
                    filterResults.count = resultList.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
                clear()

                if (null != results && 0 < results.count)
                    notifyDataSetChanged()
                else
                    notifyDataSetInvalidated()
            }
        }
}
