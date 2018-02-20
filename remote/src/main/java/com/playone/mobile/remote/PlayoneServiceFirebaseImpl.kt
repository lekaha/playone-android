package com.playone.mobile.remote

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

/**
 * An implementation of [com.playone.mobile.remote.PlayoneService] that retrieving
 * Playone data from Firebase and using the Firebase SDK in this class.
 */
class PlayoneServiceFirebaseImpl(
    private val playoneFirebase: PlayoneFirebase
) : PlayoneService {
    override fun fetchPlayoneList(userId: Int) = TODO()

    override fun fetchJoinedPlayoneList(userId: Int) = TODO()

    override fun fetchFavoritePlayoneList(userId: Int) = TODO()

    override fun fetchPlayoneDetail(playoneId: Int) = TODO()

    override fun createPlayoneDetail(userId: Int, playoneModel: PlayoneModel) = TODO()

    override fun updatePlayoneDetail(userId: Int, playoneModel: PlayoneModel) = TODO()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) = TODO()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isJoint(playoneId: Int, userId: Int) = TODO()

    //region For Auth0
    override fun userModel(userId: Int) = TODO()

    override fun createUser(userModel: UserModel) = TODO()

    override fun updateUser(userModel: UserModel) = TODO()

    override fun updateUser(userModel: UserModel, lastDeviceToken: String) = TODO()
    //endregion

    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()
}