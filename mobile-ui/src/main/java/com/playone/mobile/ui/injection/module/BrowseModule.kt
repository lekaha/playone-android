package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseAdapter
import com.playone.mobile.ui.browse.BrowseViewHolder
import com.playone.mobile.ui.model.BufferooViewModel.Companion.DISPLAY_TYPE_BROWSE
import com.playone.mobile.ui.view.recycler.ItemComparator
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap

@Module
abstract class BrowseModule {

    @Binds
    @IntoMap
    @IntKey(DISPLAY_TYPE_BROWSE)
    abstract fun provideBrowseViewHolderFactory(factory: BrowseViewHolder.BrowseViewHolderFactory)
            : ViewHolderFactory

    @Binds
    @IntoMap
    @IntKey(DISPLAY_TYPE_BROWSE)
    abstract fun provideBrowseViewHolderBinder(binder: BrowseViewHolder.BrowseViewHolderBinder)
            : ViewHolderBinder

    @Module
    companion object {

        @JvmStatic @Provides
        fun provideRecyclerAdapter(itemComparator: ItemComparator,
                                   factoryMap: Map<Int, @JvmSuppressWildcards ViewHolderFactory>,
                                   binderMap: Map<Int, @JvmSuppressWildcards ViewHolderBinder>)
                = BrowseAdapter(itemComparator, factoryMap, binderMap)

        @JvmStatic @Provides
        fun provideComparator(): ItemComparator = BrowseAdapter.BrowseItemComparator()
    }
}