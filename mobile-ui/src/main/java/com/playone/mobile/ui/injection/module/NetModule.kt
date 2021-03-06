package com.playone.mobile.ui.injection.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.playone.mobile.remote.bridge.playone.PlayoneFirebase
import com.playone.mobile.remote.bridge.playone.PlayoneServiceFactory
import com.playone.mobile.ui.BuildConfig
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
import com.playone.mobile.ui.injection.scopes.PerApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [FirebaseModule::class])
open class NetModule {

    companion object {
        const val BASE_URL = "BASE_URL"
        const val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
        const val READ_TIMEOUT = "READ_TIMEOUT"
    }

    @Provides
    @PerApplication
    internal fun providePlayoneService(playoneFirebase: PlayoneFirebase) =
        PlayoneServiceFactory.makeFirebaseService(BuildConfig.DEBUG, playoneFirebase)

    @Provides
    @PerApplication
    internal fun provideChuckInterceptor(@ApplicationContext context: Context) =
        ChuckInterceptor(context)

    @Provides
    internal fun provideStethoInterceptor() = StethoInterceptor()

    @Provides
    @Named(BASE_URL)
    internal fun provideBaseUrl() = "https://joe-birch-dsdb.squarespace.com/s/"

    @Provides
    @Named(CONNECT_TIMEOUT)
    internal fun provideConnectTimeout() = 120L

    @Provides
    @Named(READ_TIMEOUT)
    internal fun provideReadTimeout() = 120L
}