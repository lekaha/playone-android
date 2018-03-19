package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.interactor.playone.GetOwnPlayoneList
import com.playone.mobile.domain.interactor.playone.GetPlayoneList
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.getPlayoneList.GetPlayoneListContract
import com.playone.mobile.presentation.getPlayoneList.GetPlayoneListPresenter
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
import com.playone.mobile.ui.model.PlayoneListViewModel
import com.playone.mobile.ui.playone.PlayoneViewHolder
import dagger.Module
import dagger.Provides

@Module
class PlayoneListModule {

    @Provides
    internal fun providePlayoneViewHolderFactory(@ApplicationContext context: Context) =
        PlayoneViewHolder.PlayoneViewHolderFactory(context)

    @Provides
    internal fun providePlayoneViewHolderBinder() = PlayoneViewHolder.PlayoneViewHolderBinder()

    @Provides
    @JvmSuppressWildcards
    internal fun provideLoginPresenter(
        getCurrentUser: GetCurrentUser,
        getPlayoneList: GetPlayoneList,
        getOwnPlayoneList: GetOwnPlayoneList,
        viewMapper: Mapper<PlayoneView, Playone>
    ): GetPlayoneListContract.Presenter =
        GetPlayoneListPresenter(getCurrentUser, getPlayoneList, getOwnPlayoneList, viewMapper)

    @Provides
    internal fun providePlayoneListViewModelFactory(presenter: GetPlayoneListContract.Presenter) =
        PlayoneListViewModel.PlayoneListViewModelFactory(presenter)
}