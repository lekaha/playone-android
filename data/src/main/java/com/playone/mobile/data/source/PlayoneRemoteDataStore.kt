package com.playone.mobile.data.source

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneDataStore
import com.playone.mobile.data.repository.PlayoneRemote

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the remote data source [PlayoneRemote].
 */
open class PlayoneRemoteDataStore(private val remote: PlayoneRemote) : PlayoneDataStore {

    override fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
            remote.createPlayoneDetail(userId, playoneEntity)

    override fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
            remote.updatePlayoneDetail(userId, playoneEntity)

    override fun deletePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
            remote.deletePlayoneDetail(userId, playoneEntity)

    override fun fetchPlayoneDetail(userId: String, playoneId: String) =
            remote.fetchPlayoneDetail(userId, playoneId)

    override fun fetchPlayoneList(userId: String) = remote.fetchPlayoneList(userId)

    override fun fetchFavoritePlayoneList(userId: String) = remote.fetchFavoritePlayoneList(userId)

    override fun fetchJoinedPlayoneList(userId: String) = remote.fetchJoinedPlayoneList(userId)

    override fun fetchAllPlayoneList(userId: String) = remote.fetchAllPlayoneList(userId)

    override fun createUser(userEntity: UserEntity) = remote.createUser(userEntity)

    override fun updateUser(userId: String, userEntity: UserEntity) =
        remote.updateUser(userId, userEntity)

    override fun deleteUser(userEntity: UserEntity) = remote.deleteUser(userEntity)

    override fun fetchUserByEmail(email: String) = remote.fetchUserByEmail(email)

    override fun fetchUserById(userId: String) = remote.fetchUserById(userId)

    fun joinPlayone(userId: String, playoneId: String, message: String) =
            remote.sendJoinPlayoneRequest(userId, playoneId, message)

    fun responseJoinPlayoneRequest(playoneId: String, accept: Boolean, message: String) =
            remote.responseJoinPlayoneRequest(playoneId, accept, message)

    override fun favoritePlayone(playoneId: String, userId: String, isFavorite: Boolean) =
            remote.toggleFavorite(playoneId, userId)

    override fun isFavorite(playoneId: String, userId: String) =
            remote.isFavorited(playoneId, userId)

    fun isJoined(playoneId: String, userId: String) = remote.isJoined(playoneId, userId)

//    //region Firebase Notification
//    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()
//    //endregion

}
