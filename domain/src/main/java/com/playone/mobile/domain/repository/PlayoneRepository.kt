package com.playone.mobile.domain.repository

import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented.
 */
interface PlayoneRepository {

    //region For caching the data by the cache layer.
//    fun clearPlayoneList(): Completable
//
//    fun savePlayoneList(playoneList: List<Playone>): Completable
//
//    fun clearJoinedPlayoneList(): Completable
//
//    fun saveJoinedPlayoneList(playoneList: List<Playone>): Completable
//
//    fun clearFavoritePlayoneList(): Completable
//
//    fun saveFavoritePlayoneList(playoneList: List<Playone>): Completable
//
//    fun clearPlayoneDetail(): Completable
//
//    fun savePlayoneDetail(userId: String, playone: Playone): Single<Playone>
//
//    fun clearUser(user: User): Completable
//
//    fun createUser(user: User): Completable
//
//    fun saveUser(user: User): Completable
    //endregion

    //region For Playone API
    fun createPlayone(userId: String, playone: Playone): Single<Playone>

    fun updatePlayone(userId: String, playone: Playone): Single<Playone>

    fun joinTeam(playoneId: String, userId: String, isJoin: Boolean): Single<Boolean>

    fun sendJoinRequest(playoneId: String, userId: String, msg: String): Single<Boolean>

    fun toggleFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isJoined(playoneId: String, userId: String): Single<Boolean>

    fun getUserByEmail(email: String): Single<User>

    fun getUserById(userId: String): Single<User>

    fun getPlayoneDetail(playoneId: String): Single<Playone>

    fun getFavoritePlayoneList(userId: String): Single<List<Playone>>

    fun getJoinedPlayoneList(userId: String): Single<List<Playone>>

    fun getPlayoneList(userId: String): Single<List<Playone>>
    //endregion
}