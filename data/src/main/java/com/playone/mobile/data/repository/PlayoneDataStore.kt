package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to
 * [com.playone.mobile.data.model.PlayoneItem]. This is to be implemented by external
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
}