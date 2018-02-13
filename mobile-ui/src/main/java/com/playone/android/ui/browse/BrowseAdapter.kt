package com.playone.android.ui.browse

import com.playone.android.ui.view.recycler.DisplayableItem
import com.playone.android.ui.view.recycler.ItemComparator
import com.playone.android.ui.view.recycler.RecyclerViewAdapter
import com.playone.android.ui.view.recycler.ViewHolderFactory
import com.playone.android.ui.view.recycler.ViewHolderBinder

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