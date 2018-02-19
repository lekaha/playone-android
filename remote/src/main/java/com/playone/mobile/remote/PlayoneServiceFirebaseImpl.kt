package com.playone.mobile.remote

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity

/**
 * An implementation of [com.playone.mobile.remote.PlayoneService] that retrieving
 * Playone data from Firebase and using the Firebase SDK in this class.
 */
class PlayoneServiceFirebaseImpl : PlayoneService {

    override fun fetchPlayoneList(userId: Int) = TODO()

    override fun fetchJoinedPlayoneList(userId: Int) = TODO()

    override fun fetchFavoritePlayoneList(userId: Int) = TODO()

    override fun fetchPlayoneDetail(playoneId: Int) = TODO()

    override fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) = TODO()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isJoint(playoneId: Int, userId: Int) = TODO()

    override fun userEntity(userId: Int) = TODO()

    override fun createUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) = TODO()

    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()
}