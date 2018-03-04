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

    fun clearPlayoneList(): Completable

    fun savePlayoneList(playoneList: List<Playone>): Completable

    fun getPlayoneList(userId: Int = -1): Single<List<Playone>>

    fun clearJoinedPlayoneList(): Completable

    fun saveJoinedPlayoneList(playoneList: List<Playone>): Completable

    fun getJoinedPlayoneList(userId: Int): Single<List<Playone>>

    fun clearFavoritePlayoneList(): Completable

    fun saveFavoritePlayoneList(playoneList: List<Playone>): Completable

    fun getFavoritePlayoneList(userId: Int): Single<List<Playone>>

    fun clearPlayoneDetail(): Completable

    fun savePlayoneDetail(playone: Playone): Completable

    fun getPlayoneDetail(playoneId: Int): Single<Playone>

    fun clearUser(user: User): Completable

    fun createUser(user: User): Completable

    fun saveUser(user: User): Completable

    fun getUserByEmail(email: String): Single<User>

    fun getUserById(userId: Int): Single<User>

}