package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.model.PlayoneParticipatorItemViewModel.Companion.DISPLAY_TYPE_PARTICIPATOR
import com.playone.mobile.ui.playone.PlayoneAdapter
import com.playone.mobile.ui.playone.PlayoneDetailViewHolder
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
    @IntKey(DISPLAY_TYPE_PARTICIPATOR)
    abstract fun providePlayoneDetailViewHolderFactory(
        factory: PlayoneDetailViewHolder.PlayoneViewHolderFactory
    ): ViewHolderFactory

    @Binds
    @IntoMap
    @IntKey(DISPLAY_TYPE_PARTICIPATOR)
    abstract fun providePlayoneDetailViewHolderBinder(
        binder: PlayoneDetailViewHolder.PlayoneViewHolderBinder
    ): ViewHolderBinder

    @Module
    companion object {

        @JvmStatic
        @Provides
        @JvmSuppressWildcards
        fun provideRecyclerAdapter(
            itemComparator: ItemComparator,
            factoryMap: Map<Int, ViewHolderFactory>,
            binderMap: Map<Int, ViewHolderBinder>
        ) = PlayoneAdapter(itemComparator, factoryMap, binderMap)

        @JvmStatic
        @Provides
        fun provideComparator(): ItemComparator = PlayoneAdapter.PlayoneItemComparator()
    }
}