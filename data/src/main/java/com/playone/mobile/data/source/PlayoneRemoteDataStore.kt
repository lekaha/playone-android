package com.playone.mobile.data.source

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneDataStore
import com.playone.mobile.data.repository.PlayoneRemote

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the remote data source [PlayoneRemote].
 */
open class PlayoneRemoteDataStore(private val remote: PlayoneRemote) : PlayoneDataStore {

    /**
     * Retrieve a list of [PlayoneEntity] instances from the API.
     *
     * @param userId user id.
     */
    override fun fetchPlayoneList(userId: Int) = remote.fetchPlayoneList(userId)

    /**
     * Retrieve a list of the joined [PlayoneEntity] instances from the API.
     *
     * @param userId user id.
     */
    override fun fetchJoinedPlayoneList(userId: Int) = remote.fetchJoinedPlayoneList(userId)

    /**
     * Retrieve a list of the favorite [PlayoneEntity] instances from the API.
     *
     * @param userId user id.
     */
    override fun fetchFavoritePlayoneList(userId: Int) = remote.fetchFavoritePlayoneList(userId)

    /**
     * Retrieve an entity [PlayoneEntity] detail instances from the API.
     *
     * @param playoneId playone id.
     */
    override fun fetchPlayoneDetail(playoneId: Int) = remote.fetchPlayoneDetail(playoneId)

    override fun createUser(userEntity: UserEntity) = remote.createUser(userEntity)

    /**
     * Retrieve an entity [UserEntity] instances from the API by [userId].
     *
     * @param userId user id.
     */
    override fun fetchUserEntity(userId: Int) = remote.fetchUserEntity(userId)

    /**
     * Retrieve an entity [UserEntity] instances from the API by [email].
     *
     * @param email user's email.
     */
    override fun fetchUserEntity(email: String) = remote.fetchUserEntity(email)

    override fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) = TODO()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isJoint(playoneId: Int, userId: Int) = TODO()

    override fun updateUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) = TODO()

    //region Firebase Notification
    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()
    //endregion

    //region Unsupported operation for the remote data store.
    override fun clearPlayoneList() = throw UnsupportedOperationException()

    override fun savePlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    override fun clearJoinedPlayoneList() = throw UnsupportedOperationException()

    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    override fun clearFavoritePlayoneList() = throw UnsupportedOperationException()

    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    override fun clearPlayoneDetail() = throw UnsupportedOperationException()

    override fun savePlayoneDetail(playoneEntity: PlayoneEntity) =
        throw UnsupportedOperationException()

    override fun clearUserEntity(userEntity: UserEntity) = throw UnsupportedOperationException()

    override fun saveUserEntity(userEntity: UserEntity) =
        throw UnsupportedOperationException()
    //endregion

}