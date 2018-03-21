package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.model.PlayoneListItemViewModel.Companion.DISPLAY_TYPE_PLAYONE
import com.playone.mobile.ui.playone.PlayoneAdapter
import com.playone.mobile.ui.playone.PlayoneViewHolder
import com.playone.mobile.ui.view.recycler.ItemComparator
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap

@Module
abstract class PlayoneDetailBindModule {

    @Binds
    @IntoMap
    @IntKey(DISPLAY_TYPE_PLAYONE)
    abstract fun providePlayoneDetailViewHolderFactory(
        factory: PlayoneViewHolder.PlayoneViewHolderFactory
    ): ViewHolderFactory

    @Binds
    @IntoMap
    @IntKey(DISPLAY_TYPE_PLAYONE)
    abstract fun providePlayoneDetailViewHolderBinder(
        binder: PlayoneViewHolder.PlayoneViewHolderBinder
    ): ViewHolderBinder

    @Module
    companion object {

        @JvmStatic @Provides
        fun provideRecyclerAdapter(
            itemComparator: ItemComparator,
            factoryMap: Map<Int, @JvmSuppressWildcards ViewHolderFactory>,
            binderMap: Map<Int, @JvmSuppressWildcards ViewHolderBinder>
        ) = PlayoneAdapter(itemComparator, factoryMap, binderMap)

        @JvmStatic @Provides
        fun provideComparator(): ItemComparator = PlayoneAdapter.PlayoneItemComparator()
    }
}