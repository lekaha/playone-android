package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to
 * [com.playone.mobile.data.model.PlayoneEntity]. This is to be implemented by external
 * data source layers, setting the requirements for the operations that need to
 * be implemented.
 */
interface PlayoneDataStore {

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
    fun fetchPlayoneList(userId: String): Single<List<PlayoneEntity>>

    /**
     * fetch Favorite Playone list by user Id who has favorited Playones
     *
     * @param userId who is fetching
     */
    fun fetchFavoritePlayoneList(userId: String): Single<List<PlayoneEntity>>

    /**
     * fetch Joined Playone list by user Id who has joined Playones
     *
     * @param userId who is fetching
     */
    fun fetchJoinedPlayoneList(userId: String): Single<List<PlayoneEntity>>

    /**
     * fetch All Playone list by user Id
     *
     * @param userId who is fetching
     */
    fun fetchAllPlayoneList(userId: String): Single<List<PlayoneEntity>>

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
    fun updateUser(userId: String, userEntity: UserEntity): Single<UserEntity>

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

    /**
     * fetch User info through User ID
     *
     * @param userId
     */
    fun fetchUserById(userId: String): Single<UserEntity>

    /**
     * set favorite playone
     *
     * @param playoneId Playone ID
     * @param userId User ID
     * @param isFavorite true or false
     */
    fun favoritePlayone(playoneId: String, userId: String, isFavorite: Boolean): Single<Boolean>

    /**
     * Get the result if favorite the playone
     *
     * @param playoneId Playone ID
     * @param userId User ID
     */
    fun isFavorite(playoneId: String, userId: String): Single<Boolean>
}
