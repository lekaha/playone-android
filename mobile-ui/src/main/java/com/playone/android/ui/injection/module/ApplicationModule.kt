package com.playone.android.ui.injection.module

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.playone.android.cache.BufferooCacheImpl
import com.playone.android.cache.PreferencesHelper
import com.playone.android.cache.db.DbOpenHelper
import com.playone.android.cache.mapper.BufferooEntityMapper
import com.playone.android.data.BufferooDataRepository
import com.playone.android.data.executor.JobExecutor
import com.playone.android.data.mapper.BufferooMapper
import com.playone.android.data.repository.BufferooCache
import com.playone.android.data.repository.BufferooRemote
import com.playone.android.data.source.BufferooDataStoreFactory
import com.playone.android.domain.executor.PostExecutionThread
import com.playone.android.domain.executor.ThreadExecutor
import com.playone.android.domain.repository.BufferooRepository
import com.playone.android.remote.BufferooRemoteImpl
import com.playone.android.remote.BufferooService
import com.playone.android.ui.UiThread
import com.playone.android.ui.injection.scopes.PerApplication
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
    internal fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }


    @Provides
    @PerApplication
    internal fun provideBufferooRepository(factory: BufferooDataStoreFactory,
                                           mapper: BufferooMapper): BufferooRepository {
        return BufferooDataRepository(factory, mapper)
    }

    @Provides
    @PerApplication
    internal fun provideBufferooCache(factory: DbOpenHelper,
                                      entityMapper: BufferooEntityMapper,
                                      mapper: com.playone.android.cache.db.mapper.BufferooMapper,
                                      helper: PreferencesHelper): BufferooCache {
        return BufferooCacheImpl(factory, entityMapper, mapper, helper)
    }

    @Provides
    @PerApplication
    internal fun provideBufferooRemote(service: BufferooService,
                                       factory: com.playone.android.remote.mapper.BufferooEntityMapper): BufferooRemote {
        return BufferooRemoteImpl(service, factory)
    }

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }
}
