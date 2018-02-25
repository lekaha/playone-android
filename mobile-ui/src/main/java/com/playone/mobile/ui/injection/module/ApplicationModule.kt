package com.playone.mobile.ui.injection.module

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.data.executor.JobExecutor
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.ui.UiThread
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
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
    @ApplicationContext
    fun provideContext(application: Application): Context {
        init(application)
        return application
    }

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(@ApplicationContext context: Context)
            = PreferencesHelper(context)

    @Provides
    @PerApplication
    internal fun provideDbOpenHelper(@ApplicationContext context: Context) = DbOpenHelper(context)

    @Provides
    @PerApplication
    internal fun provideJobExecutor() = JobExecutor()

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @PerApplication
    internal fun provideUiThread() = UiThread()

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread
}
