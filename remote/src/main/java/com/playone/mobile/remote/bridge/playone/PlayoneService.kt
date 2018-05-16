package com.playone.mobile.remote.bridge.playone

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import io.reactivex.Single

/**
 * Defines the interface methods used for interacting with the Playone API.
 */
interface PlayoneService {

    //region Playone
    fun retrievePlayoneList(): Single<List<PlayoneModel>>

    fun retrievePlayoneList(userId: String): Single<List<PlayoneModel>>

    fun retrieveJoinedPlayoneList(userId: String): Single<List<PlayoneModel>>

    fun retrieveFavoritePlayoneList(userId: String): Single<List<PlayoneModel>>

    fun retrievePlayoneDetail(playoneId: String): Single<PlayoneModel>

    fun createPlayoneDetail(userId: String, playoneModel: PlayoneModel): Single<PlayoneModel>

    fun updatePlayoneDetail(userId: String, playoneModel: PlayoneModel): Single<PlayoneModel>

    fun joinTeamAsMember(playoneId: String, userId: String, isJoin: Boolean): Single<PlayoneRemote.Result>

    fun sendJoinRequest(playoneId: String, userId: String, msg: String = ""): Single<PlayoneRemote.Result>

    fun toggleFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isJoined(playoneId: String, userId: String): Single<Boolean>
    //endregion

    //region User for Auth
    fun retrieveUserModel(userId: String): Single<UserModel>

    fun retrieveUserModelByEmail(email: String): Single<UserModel>

    fun createUser(userModel: UserModel): Single<UserModel>

    fun updateUser(userModel: UserModel): Single<UserModel>

    fun updateUser(userModel: UserModel, lastDeviceToken: String): Single<UserModel>
    //endregion

    //region Notification
    fun applyNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun acceptedNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun acceptNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun dismissNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun kickNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun quitNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun rejectedNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>

    fun rejectNotification(payload: NotificationPayloadEntity): Single<PlayoneRemote.Result>
    //endregion

}
