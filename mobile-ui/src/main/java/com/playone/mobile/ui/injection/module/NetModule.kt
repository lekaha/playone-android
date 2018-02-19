package com.playone.mobile.ui.injection.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.playone.mobile.remote.BufferooServiceFactory
import com.playone.mobile.remote.PlayoneServiceFactory
import com.playone.mobile.ui.BuildConfig
import com.playone.mobile.ui.injection.scopes.PerApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
open class NetModule {
    companion object {
        const val BASE_URL = "BASE_URL"
        const val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
        const val READ_TIMEOUT = "READ_TIMEOUT"
    }


    @Provides
    @PerApplication
    internal fun provideBufferooService(
            @Named(BASE_URL) baseUrl: String,
            @Named(CONNECT_TIMEOUT) connectTimeout: Long,
            @Named(READ_TIMEOUT) readTimeout: Long,
            chuckInterceptor: ChuckInterceptor,
            stethoInterceptor: StethoInterceptor
    ) = BufferooServiceFactory.makeService(
            BuildConfig.DEBUG,
            baseUrl,
            connectTimeout,
            readTimeout,
            arrayOf(chuckInterceptor, stethoInterceptor))

    @Provides
    @PerApplication
    internal fun providePlayoneService() = PlayoneServiceFactory.makeService(BuildConfig.DEBUG)

    @Provides
    internal fun provideChuckInterceptor(context: Context) = ChuckInterceptor(context)

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