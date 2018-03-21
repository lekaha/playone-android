package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.domain.interactor.playone.GetPlayoneDetail
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.getPlayoneDetail.GetPlayoneDetailContract
import com.playone.mobile.presentation.getPlayoneDetail.GetPlayoneDetailPresenter
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
import com.playone.mobile.ui.model.PlayoneDetailViewModel
import com.playone.mobile.ui.playone.PlayoneDetailViewHolder
import dagger.Module
import dagger.Provides

@Module
class PlayoneDetailModule {

    @Provides
    internal fun providePlayoneDetailViewHolderFactory(@ApplicationContext context: Context) =
        PlayoneDetailViewHolder.PlayoneViewHolderFactory(context)

    @Provides
    internal fun providePlayoneDetailViewHolderBinder() =
        PlayoneDetailViewHolder.PlayoneViewHolderBinder()

    @Provides
    @JvmSuppressWildcards
    internal fun providePlayoneDetailPresenter(
        getPlayoneDetail: GetPlayoneDetail,
        viewMapper: Mapper<PlayoneView, Playone>
    ): GetPlayoneDetailContract.Presenter = GetPlayoneDetailPresenter(getPlayoneDetail, viewMapper)

    @Provides
    internal fun providePlayoneDetailViewModelFactory(presenter: GetPlayoneDetailContract.Presenter) =
        PlayoneDetailViewModel.PlayoneDetailViewModelFactory(presenter)
}