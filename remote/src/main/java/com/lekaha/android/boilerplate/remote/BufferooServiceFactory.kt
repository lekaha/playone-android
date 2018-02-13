package com.lekaha.android.boilerplate.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create instances of [BufferooService]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
object BufferooServiceFactory {

    fun makeBuffeoorService(isDebug: Boolean, vararg interceptors: Interceptor): BufferooService {
        val okHttpClient = makeOkHttpClient(isDebug, *interceptors)
        return makeBufferooService(okHttpClient, makeGson())
    }

    private fun makeBufferooService(okHttpClient: OkHttpClient, gson: Gson): BufferooService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://joe-birch-dsdb.squarespace.com/s/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(BufferooService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private fun makeOkHttpClient(isDebug: Boolean, vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(makeLoggingInterceptor(isDebug))
        if (isDebug) interceptors.forEach { builder.addInterceptor(it) }
        return builder
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
          HttpLoggingInterceptor.Level.NONE
        return logging
    }

}