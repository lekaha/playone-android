package com.playone.mobile.remote

import com.playone.mobile.remote.model.BufferooModel
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface BufferooService {
    @GET("team.json")
    fun getBufferoos(): Single<BufferooResponse>

    class BufferooResponse {
        lateinit var team: List<BufferooModel>
    }
}
