package com.playone.android.ui.injection.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.playone.android.remote.BufferooService
import com.playone.android.remote.BufferooServiceFactory
import com.playone.android.ui.BuildConfig
import com.playone.android.ui.injection.scopes.PerApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides

@Module
open class NetModule {

    @Provides
    @PerApplication
    internal fun provideBufferooService(chuckInterceptor: ChuckInterceptor,
                                        stethoInterceptor: StethoInterceptor
                                        ): BufferooService {
        return BufferooServiceFactory.makeBuffeoorService(
                BuildConfig.DEBUG,
                chuckInterceptor,
                stethoInterceptor)
    }

    @Provides
    internal fun provideChuckInterceptor(context: Context): ChuckInterceptor
            = ChuckInterceptor(context)

    @Provides
    internal fun provideStethoInterceptor(): StethoInterceptor
            = StethoInterceptor()
}