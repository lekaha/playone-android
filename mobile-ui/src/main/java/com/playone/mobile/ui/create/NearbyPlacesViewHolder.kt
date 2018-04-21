package com.playone.mobile.ui.create

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.location.places.AutocompletePrediction
import com.playone.mobile.ui.R
import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory

class NearbyPlacesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var titleText: TextView = view.findViewById(R.id.text_title)
    private var descriptionText: TextView = view.findViewById(R.id.text_description)

    fun bind(autocompletePrediction: AutocompletePrediction) {

        titleText.text = autocompletePrediction.getPrimaryText(null)
        descriptionText.text = autocompletePrediction.getSecondaryText(null)
    }

    class NearbyPlacesViewHolderFactory constructor(context: Context) :
        ViewHolderFactory(context) {

        override fun createViewHolder(parent: ViewGroup) =
            NearbyPlacesViewHolder(LayoutInflater
                                       .from(context)
                                       .inflate(R.layout.item_search_places_result,
                                                parent,
                                                false))
    }

    class NearbyPlacesViewHolderBinder : ViewHolderBinder {

        override fun bind(
            viewHolder: RecyclerView.ViewHolder,
            item: DisplayableItem<*>
        ) {
            val nearbyPlacesViewHolder = NearbyPlacesViewHolder::class.java.cast(viewHolder)
            val model = AutocompletePrediction::class.java.cast(item.model())
            val click = item.click() as (AutocompletePrediction) -> Unit

            nearbyPlacesViewHolder.itemView.setOnClickListener{ click(model) }
            nearbyPlacesViewHolder.bind(model)
        }

    }
}