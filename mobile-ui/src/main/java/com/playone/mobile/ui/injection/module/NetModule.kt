package com.playone.mobile.ui.injection.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.playone.mobile.remote.BufferooServiceFactory
import com.playone.mobile.ui.BuildConfig
import com.playone.mobile.ui.injection.scopes.PerApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides

@Module
open class NetModule {

    @Provides
    @PerApplication
    internal fun provideBufferooService(
        chuckInterceptor: ChuckInterceptor,
        stethoInterceptor: StethoInterceptor
    ) = BufferooServiceFactory.makeBuffeoorService(BuildConfig.DEBUG,
                                                   arrayOf(chuckInterceptor, stethoInterceptor))

    @Provides
    internal fun provideChuckInterceptor(context: Context) = ChuckInterceptor(context)

    @Provides
    internal fun provideStethoInterceptor() = StethoInterceptor()
}