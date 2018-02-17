package com.playone.mobile.remote.service

import com.google.gson.Gson
import com.playone.mobile.remote.PlayoneService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 */
object PlayoneServiceFactory : ServiceFactory<PlayoneService>() {
    override fun makeService(isDebug: Boolean, interceptors: Array<Interceptor>) =
        makeOkHttpClient(isDebug, interceptors).let { makeService(it, makeGson()) }

    override fun makeService(okHttpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .run { create(PlayoneService::class.java) }
}