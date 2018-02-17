package com.playone.mobile.remote.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create instances of [ServiceFactory]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
abstract class ServiceFactory<out S> {
    protected abstract fun makeService(isDebug: Boolean, interceptors: Array<Interceptor>): S

    protected abstract fun makeService(okHttpClient: OkHttpClient, gson: Gson): S

    protected fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

    protected fun makeOkHttpClient(isDebug: Boolean, interceptors: Array<Interceptor>) =
        OkHttpClient.Builder().apply {
            addInterceptor(makeLoggingInterceptor(isDebug))
            if (isDebug) interceptors.forEach { addInterceptor(it) }
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
        }.build()

    protected fun makeGson() =
        GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    protected fun makeLoggingInterceptor(isDebug: Boolean) =
        HttpLoggingInterceptor().apply {
            level = if (isDebug) Level.BODY else Level.NONE
        }
}