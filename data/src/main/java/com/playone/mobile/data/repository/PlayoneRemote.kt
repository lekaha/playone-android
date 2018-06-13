package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import io.reactivex.Single

/**
 * Interface defining methods for the retrieving of the data. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface PlayoneRemote {

    /**
     * create Playone detail by user id
     *
     * @param userId who is creating
     * @param playoneEntity the Playone info
     */
    fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>

    /**
     * update Playone detail by user id
     *
     * @param userId who is updating
     * @param playoneEntity the Playone info
     */
    fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>

    /**
     * delete Playone detail by user id
     *
     * @param userId who is deleting
     * @param playoneEntity the Playone info
     */
    fun deletePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>

    /**
     * fetch Playone detail by user id
     *
     * @param userId who is fetching
     * @param playoneId the Playone's id which will be fetched
     */
    fun fetchPlayoneDetail(userId: String, playoneId: String): Single<PlayoneEntity>

    /**
     * fetch Playone list by user Id who owns all of Playones
     *
     * @param userId who is fetching
     */
    fun fetchPlayoneList(userId: String): Single<PlayoneEntity>

    /**
     * fetch Favorite Playone list by user Id who has favorited Playones
     *
     * @param userId who is fetching
     */
    fun fetchFavoritePlayoneList(userId: String): Single<PlayoneEntity>

    /**
     * fetch Joined Playone list by user Id who has joined Playones
     *
     * @param userId who is fetching
     */
    fun fetchJoinedPlayoneList(userId: String): Single<PlayoneEntity>

    /**
     * create User
     *
     * @param userEntity User info
     */
    fun createUser(userEntity: UserEntity): Single<UserEntity>

    /**
     * update User
     *
     * @param userEntity User info
     */
    fun updateUser(userEntity: UserEntity): Single<UserEntity>

    /**
     * delete User
     *
     * @param userEntity User info
     */
    fun deleteUser(userEntity: UserEntity): Single<UserEntity>

    /**
     * fetch User info through Email
     *
     * @param email
     */
    fun fetchUserByEmail(email: String): Single<UserEntity>



//    //region Playone
//    fun fetchPlayoneList(): Single<List<PlayoneEntity>>
//
//    fun fetchPlayoneList(userId: String): Single<List<PlayoneEntity>>
//
//    fun fetchJoinedPlayoneList(userId: String): Single<List<PlayoneEntity>>
//
//    fun fetchFavoritePlayoneList(userId: String): Single<List<PlayoneEntity>>
//
//    fun fetchPlayoneDetail(playoneId: String): Single<PlayoneEntity>
//
//    fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>
//
//    fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity>
//
//    fun joinTeamAsMember(playoneId: String, userId: String, isJoin: Boolean): Single<Result>
//
//    fun sendJoinRequest(playoneId: String, userId: String, msg: String = ""): Single<Result>
//
//    fun toggleFavorite(playoneId: String, userId: String): Single<Boolean>
//
//    fun isFavorite(playoneId: String, userId: String): Single<Boolean>
//
//    fun isJoined(playoneId: String, userId: String): Single<Boolean>
//    //endregion
//
//    //region User for Auth
//    fun fetchUserEntity(userId: String): Single<UserEntity>
//
//    fun fetchUserEntityByEmail(email: String): Single<UserEntity>
//
//    fun createUser(userEntity: UserEntity): Single<UserEntity>
//
//    fun updateUser(userEntity: UserEntity): Single<UserEntity>
//
//    fun updateUser(userEntity: UserEntity, lastDeviceToken: String): Single<UserEntity>
//    //endregion

//    //region Notification
//    fun applyNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun acceptedNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun acceptNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun dismissNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun kickNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun quitNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun rejectedNotification(payload: NotificationPayloadEntity): Single<Result>
//
//    fun rejectNotification(payload: NotificationPayloadEntity): Single<Result>
    //endregion

//    enum class Result {
//        SUCCESS,
//        ERROR
//    }
}

