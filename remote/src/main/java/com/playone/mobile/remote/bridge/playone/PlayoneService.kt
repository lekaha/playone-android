package com.playone.mobile.remote.bridge.playone

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import io.reactivex.Completable
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

    fun joinTeamAsMember(playoneId: String, userId: String, isJoin: Boolean): Completable

    fun sendJoinRequest(playoneId: String, userId: String, msg: String = ""): Completable

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
    fun applyNotification(payload: NotificationPayloadEntity): Completable

    fun acceptedNotification(payload: NotificationPayloadEntity): Completable

    fun acceptNotification(payload: NotificationPayloadEntity): Completable

    fun dismissNotification(payload: NotificationPayloadEntity): Completable

    fun kickNotification(payload: NotificationPayloadEntity): Completable

    fun quitNotification(payload: NotificationPayloadEntity): Completable

    fun rejectedNotification(payload: NotificationPayloadEntity): Completable

    fun rejectNotification(payload: NotificationPayloadEntity): Completable
    //endregion

}
