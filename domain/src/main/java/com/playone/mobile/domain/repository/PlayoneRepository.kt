package com.playone.mobile.domain.repository

import com.playone.mobile.domain.model.Join
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

    fun createPlayone(playone: Playone): Single<Playone>

    fun updatePlayone(playone: Playone): Single<Playone>

    fun joinPlayone(join: Join): Completable

    fun favoritePlayone(playoneId: String, userId: String, isFavorite: Boolean): Single<Boolean>

    fun isFavorite(playoneId: String, userId: String): Single<Boolean>

    fun isJoined(playoneId: String, userId: String): Single<Boolean>

    fun createUser(user: User): Single<User>

    fun updateUser(userId: String, user: User): Single<User>

    fun getUserByEmail(email: String): Single<User>

    fun getUserById(userId: String): Single<User>

    fun getPlayoneDetail(userId: String, playoneId: String): Single<Playone>

    fun getFavoritePlayoneList(userId: String): Single<List<Playone>>

    fun getJoinedPlayoneList(userId: String): Single<List<Playone>>

    fun getPlayoneList(userId: String): Single<List<Playone>>
}
