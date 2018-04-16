package com.playone.mobile.ui.create

import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ItemComparator
import com.playone.mobile.ui.view.recycler.RecyclerViewAdapter
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory

class NearbyPlacesAdapter(itemComparator: ItemComparator,
                          factoryMap: Map<Int, ViewHolderFactory>,
                          binderMap: Map<Int, ViewHolderBinder>):
        RecyclerViewAdapter(itemComparator, factoryMap, binderMap) {


    class NearbyPlaceItemComparator: ItemComparator {
        override fun areItemsTheSame(itemLeft: DisplayableItem<*>,
                                     itemRight: DisplayableItem<*>) = itemLeft == itemRight

        override fun areContentsTheSame(itemLeft: DisplayableItem<*>,
                                        itemRight: DisplayableItem<*>) = itemLeft == itemRight

    }
}