package com.playone.mobile.remote

import com.playone.mobile.data.model.PlayoneEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Defines the interface methods used for interacting with the Playone API.
 */
interface PlayoneService {
    @GET("/playones/groups")
    fun getPlayoneEntities(): Single<PlayoneEntity>

    @GET("/playones/groups/{id}")
    fun getPlayoneEntity(@Path("id") playoneId: String): Single<List<PlayoneEntity>>
}
