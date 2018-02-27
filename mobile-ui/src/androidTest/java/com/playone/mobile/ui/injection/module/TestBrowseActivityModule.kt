package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.browse.GetBufferoos
import com.playone.mobile.domain.repository.BufferooRepository
import com.playone.mobile.presentation.browse.BrowseBufferoosContract
import com.playone.mobile.presentation.browse.BrowseBufferoosPresenter
import com.playone.mobile.presentation.mapper.BufferooMapper
import com.playone.mobile.ui.browse.BrowseViewHolder
import com.playone.mobile.ui.injection.qualifier.ActivityContext
import com.playone.mobile.ui.model.BrowseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
open class TestBrowseActivityModule {

    @Provides
    internal fun provideGetBufferoos(
        bufferooRepository: BufferooRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetBufferoos(bufferooRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideBufferooMapper() = BufferooMapper()

    @Provides
    internal fun provideUiBufferooMapper() =
        com.playone.mobile.ui.mapper.BufferooMapper()

    @Provides
    internal fun provideBrowseViewHolderFactory(@ActivityContext context: Context) =
        BrowseViewHolder.BrowseViewHolderFactory(context)

    @Provides
    internal fun provideBrowseViewHolderBinder() = BrowseViewHolder.BrowseViewHolderBinder()

    @Provides
    internal fun provideBrowsePresenter(getBufferoos: GetBufferoos, mapper: BufferooMapper)
            : BrowseBufferoosContract.Presenter = BrowseBufferoosPresenter(getBufferoos, mapper)

    @Provides
    internal fun provideBrowseViewModelFactory(presenter: BrowseBufferoosContract.Presenter) =
        BrowseViewModelFactory(presenter)
}