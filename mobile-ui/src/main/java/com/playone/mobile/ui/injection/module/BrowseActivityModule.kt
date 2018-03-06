package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.cache.BufferooCacheImpl
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.mapper.BufferooEntityMapper
import com.playone.mobile.data.BufferooDataRepository
import com.playone.mobile.data.repository.BufferooCache
import com.playone.mobile.data.repository.BufferooRemote
import com.playone.mobile.data.source.BufferooCacheDataStore
import com.playone.mobile.data.source.BufferooDataStoreFactory
import com.playone.mobile.data.source.BufferooRemoteDataStore
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.browse.GetBufferoos
import com.playone.mobile.domain.repository.BufferooRepository
import com.playone.mobile.presentation.browse.BrowseBufferoosContract
import com.playone.mobile.presentation.browse.BrowseBufferoosPresenter
import com.playone.mobile.presentation.mapper.BufferooMapper
import com.playone.mobile.remote.BufferooRemoteImpl
import com.playone.mobile.remote.BufferooService
import com.playone.mobile.ui.browse.BrowseViewHolder
import com.playone.mobile.ui.injection.qualifier.ActivityContext
import com.playone.mobile.ui.model.BrowseViewModelFactory
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
open class BrowseActivityModule {

    @Provides
    internal fun provideGetBufferoos(
        bufferooRepository: BufferooRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetBufferoos(bufferooRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideBufferooEntityMapper() = BufferooEntityMapper()

    @Provides
    internal fun provideBufferooMapper() = com.playone.mobile.presentation.mapper.BufferooMapper()

    @Provides
    internal fun provideDbBufferooMapper() = com.playone.mobile.cache.db.mapper.BufferooMapper()

    @Provides
    internal fun provideRemoteBufferooMapper(modelMapper: ModelMapper) =
        com.playone.mobile.remote.mapper.BufferooEntityMapper(modelMapper)

    @Provides
    internal fun provideDataBufferooMapper() = com.playone.mobile.data.mapper.BufferooMapper()

    @Provides
    internal fun provideUiBufferooMapper() = com.playone.mobile.ui.mapper.BufferooMapper()

    @Provides
    internal fun provideBufferooCache(
        factory: DbOpenHelper,
        entityMapper: BufferooEntityMapper,
        mapper: com.playone.mobile.cache.db.mapper.BufferooMapper,
        helper: PreferencesHelper
    ): BufferooCache = BufferooCacheImpl(factory, entityMapper, mapper, helper)

    @Provides
    internal fun provideBufferooRemote(
        service: BufferooService,
        factory: com.playone.mobile.remote.mapper.BufferooEntityMapper
    ): BufferooRemote = BufferooRemoteImpl(service, factory)

    @Provides
    internal fun provideBufferooDataStoreFactory(
        bufferooCache: BufferooCache,
        bufferooCacheDataStore: BufferooCacheDataStore,
        bufferooRemoteDataStore: BufferooRemoteDataStore
    ): BufferooDataStoreFactory = BufferooDataStoreFactory(
        bufferooCache,
        bufferooCacheDataStore,
        bufferooRemoteDataStore
    )

    @Provides
    internal fun provideBufferooCacheDataStore(bufferooCache: BufferooCache)
        : BufferooCacheDataStore = BufferooCacheDataStore(bufferooCache)

    @Provides
    internal fun provideBufferooRemoteDataStore(bufferooRemote: BufferooRemote)
        : BufferooRemoteDataStore = BufferooRemoteDataStore(bufferooRemote)

    @Provides
    internal fun provideBufferooRepository(
        factory: BufferooDataStoreFactory,
        mapper: com.playone.mobile.data.mapper.BufferooMapper
    ): BufferooRepository = BufferooDataRepository(factory, mapper)

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
