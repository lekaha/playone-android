package com.playone.mobile.data.repository

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.ext.invalidInt
import io.reactivex.Single

/**
 * Interface defining methods for the retrieving of the data. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface PlayoneRemote {

    //region Playone
    fun fetchPlayoneList(userId: Int = invalidInt): Single<List<PlayoneEntity>>

    fun fetchJoinedPlayoneList(userId: Int): Single<List<PlayoneEntity>>

    fun fetchFavoritePlayoneList(userId: Int): Single<List<PlayoneEntity>>

    fun fetchPlayoneDetail(playoneId: Int): Single<PlayoneEntity>

    fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity): Single<Result>

    fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity): Single<Result>

    fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean): Single<Result>

    fun sendJoinRequest(playoneId: Int, userId: Int, msg: String = ""): Single<Result>

    fun toggleFavorite(playoneId: Int, userId: Int): Single<Boolean>

    fun isFavorite(playoneId: Int, userId: Int): Single<Boolean>

    fun isJoined(playoneId: Int, userId: Int): Single<Boolean>
    //endregion

    //region User for Auth
    fun fetchUserEntity(userId: Int): Single<UserEntity>

    fun fetchUserEntity(email: String): Single<UserEntity>

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

