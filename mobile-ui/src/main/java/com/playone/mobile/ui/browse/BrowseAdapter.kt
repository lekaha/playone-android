package com.playone.mobile.ui.browse

import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ItemComparator
import com.playone.mobile.ui.view.recycler.RecyclerViewAdapter
import com.playone.mobile.ui.view.recycler.ViewHolderFactory
import com.playone.mobile.ui.view.recycler.ViewHolderBinder

class BrowseAdapter(itemComparator: ItemComparator,
                    factoryMap: Map<Int, ViewHolderFactory>,
                    binderMap: Map<Int, ViewHolderBinder>):
        RecyclerViewAdapter(itemComparator, factoryMap, binderMap) {


    class BrowseItemComparator: ItemComparator {
        override fun areItemsTheSame(itemLeft: DisplayableItem<*>,
                                     itemRight: DisplayableItem<*>): Boolean
                = itemLeft == itemRight

        override fun areContentsTheSame(itemLeft: DisplayableItem<*>,
                                        itemRight: DisplayableItem<*>): Boolean
                = itemLeft == itemRight

    }
}