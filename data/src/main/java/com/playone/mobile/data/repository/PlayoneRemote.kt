package com.playone.mobile.data.repository

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import io.reactivex.Single

/**
 * Interface defining methods for the retrieving of the data. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface PlayoneRemote {

    //region Playone
    fun fetchPlayoneList(): Single<List<PlayoneEntity>>

    fun fetchPlayoneList(userId: String): Single<List<PlayoneEntity>>

    fun fetchJoinedPlayoneList(userId: String): Single<List<PlayoneEntity>>

    fun fetchFavoritePlayoneList(userId: String): Single<List<PlayoneEntity>>

    fun fetchPlayoneDetail(playoneId: String): Single<PlayoneEntity>

    fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>

    fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>

    fun joinTeamAsMember(playoneId: String, userId: String, isJoin: Boolean): Single<Result>

    fun sendJoinRequest(playoneId: String, userId: String, msg: String = ""): Single<Result>

    fun toggleFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isJoined(playoneId: String, userId: String): Single<Boolean>
    //endregion

    //region User for Auth
    fun fetchUserEntity(userId: String): Single<UserEntity>

    fun fetchUserEntityByEmail(email: String): Single<UserEntity>

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

