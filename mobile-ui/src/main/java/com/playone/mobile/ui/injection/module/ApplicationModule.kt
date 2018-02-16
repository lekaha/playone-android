package com.playone.mobile.ui.injection.module

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.playone.mobile.cache.BufferooCacheImpl
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.mapper.BufferooEntityMapper
import com.playone.mobile.data.BufferooDataRepository
import com.playone.mobile.data.executor.JobExecutor
import com.playone.mobile.data.mapper.BufferooMapper
import com.playone.mobile.data.repository.BufferooCache
import com.playone.mobile.data.repository.BufferooRemote
import com.playone.mobile.data.source.BufferooDataStoreFactory
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.repository.BufferooRepository
import com.playone.mobile.remote.BufferooRemoteImpl
import com.playone.mobile.remote.BufferooService
import com.playone.mobile.ui.UiThread
import com.playone.mobile.ui.injection.scopes.PerApplication
import com.squareup.leakcanary.LeakCanary
import dagger.Module
import dagger.Provides

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    private fun init(application: Application) {
        Stetho.initializeWithDefaults(application)

        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(application)
    }

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        init(application)
        return application
    }

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(context: Context) = PreferencesHelper(context)


    @Provides
    @PerApplication
    internal fun provideBufferooRepository(factory: BufferooDataStoreFactory,
                                           mapper: BufferooMapper) =
        BufferooDataRepository(factory, mapper)

    @Provides
    @PerApplication
    internal fun provideBufferooCache(factory: DbOpenHelper,
                                      entityMapper: BufferooEntityMapper,
                                      mapper: com.playone.mobile.cache.db.mapper.BufferooMapper,
                                      helper: PreferencesHelper): BufferooCache =
        BufferooCacheImpl(factory, entityMapper, mapper, helper)

    @Provides
    @PerApplication
    internal fun provideBufferooRemote(service: BufferooService,
                                       factory: com.playone.mobile.remote.mapper.BufferooEntityMapper): BufferooRemote =
        BufferooRemoteImpl(service, factory)

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread
}
