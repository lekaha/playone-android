package com.playone.mobile.ui.injection.module

import android.app.Application
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.data.executor.JobExecutor
import com.playone.mobile.data.repository.BufferooCache
import com.playone.mobile.data.repository.BufferooRemote
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.repository.BufferooRepository
import com.playone.mobile.remote.BufferooService
import com.playone.mobile.ui.UiThread
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
import com.playone.mobile.ui.injection.scopes.PerApplication

@Module
class TestApplicationModule {

    @Provides
    @PerApplication
    @ApplicationContext
    fun provideContext(application: Application): Context = application

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(): PreferencesHelper = mock()

    @Provides
    @PerApplication
    internal fun provideBufferooRepository(): BufferooRepository = mock()

    @Provides
    @PerApplication
    internal fun provideBufferooCache(): BufferooCache = mock()

    @Provides
    @PerApplication
    internal fun provideBufferooRemote(): BufferooRemote = mock()

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread

    @Provides
    @PerApplication
    internal fun provideBufferooService(): BufferooService = mock()

    @Provides
    @PerApplication
    internal fun provideUiThread() = UiThread()

    @Provides
    @PerApplication
    internal fun provideJobExecutor() = JobExecutor()
}