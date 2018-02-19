package com.playone.mobile.remote

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Defines the interface methods used for interacting with the Playone API.
 */
interface PlayoneService {
//    @GET("/playones/groups")
//    fun getPlayoneEntities(): Single<PlayoneEntity>
//
//    @GET("/playones/groups/{id}")
//    fun getPlayoneEntity(@Path("id") playoneId: String): Single<List<PlayoneEntity>>

    //region Playone
    fun fetchPlayoneList(userId: Int = -1): Single<List<PlayoneEntity>>

    fun fetchJoinedPlayoneList(userId: Int): Single<List<PlayoneEntity>>

    fun fetchFavoritePlayoneList(userId: Int): Single<List<PlayoneEntity>>

    fun fetchPlayoneDetail(playoneId: Int): Single<PlayoneEntity>

    fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity): Single<Result>

    fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity): Single<Result>

    fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean): Single<Result>

    fun sendJoinRequest(playoneId: Int, userId: Int, msg: String = ""): Single<Result>

    fun toggleFavorite(playoneId: Int, userId: Int): Single<Boolean>

    fun isFavorite(playoneId: Int, userId: Int): Single<Boolean>

    fun isJoint(playoneId: Int, userId: Int): Single<Boolean>
    //endregion

    //region User for Auth0
    fun userEntity(userId: Int): Single<UserEntity>

    fun createUser(userEntity: UserEntity): Single<UserEntity>

    fun updateUser(userEntity: UserEntity): Single<UserEntity>

    fun updateUser(userEntity: UserEntity, lastDeviceToken: String): Single<UserEntity>
    //endregion

    //region Notification
    fun applyNotification(payload: NotificationPayloadEntity): Single<Result>

    fun acceptedNotification(payload: NotificationPayloadEntity): Single<Result>

    fun acceptNotification(payload: NotificationPayloadEntity): Single<Result>

    fun dismissNotification(payload: NotificationPayloadEntity): Single<Result>

    fun kickNotification(payload: NotificationPayloadEntity): Single<Result>

    fun quitNotification(payload: NotificationPayloadEntity): Single<Result>

    fun rejectedNotification(payload: NotificationPayloadEntity): Single<Result>

    fun rejectNotification(payload: NotificationPayloadEntity): Single<Result>
    //endregion

    enum class Result {
        SUCCESS,
        ERROR
    }
}
