package com.playone.mobile.remote.bridge.playone

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import io.reactivex.Single

/**
 * Defines the interface methods used for interacting with the Playone API.
 */
interface PlayoneService {
    //region Playone
    fun fetchPlayoneList(userId: Int = -1): Single<List<PlayoneModel>>

    fun fetchJoinedPlayoneList(userId: Int): Single<List<PlayoneModel>>

    fun fetchFavoritePlayoneList(userId: Int): Single<List<PlayoneModel>>

    fun fetchPlayoneDetail(playoneId: Int): Single<PlayoneModel>

    fun createPlayoneDetail(userId: Int, playoneModel: PlayoneModel): Single<Result>

    fun updatePlayoneDetail(userId: Int, playoneModel: PlayoneModel): Single<Result>

    fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean): Single<Result>

    fun sendJoinRequest(playoneId: Int, userId: Int, msg: String = ""): Single<Result>

    fun toggleFavorite(playoneId: Int, userId: Int): Single<Boolean>

    fun isFavorite(playoneId: Int, userId: Int): Single<Boolean>

    fun isJoint(playoneId: Int, userId: Int): Single<Boolean>
    //endregion

    //region User for Auth0
    fun userModel(userId: Int): Single<UserModel>

    fun createUser(userModel: UserModel): Single<UserModel>

    fun updateUser(userModel: UserModel): Single<UserModel>

    fun updateUser(userModel: UserModel, lastDeviceToken: String): Single<UserModel>
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
