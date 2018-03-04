package com.playone.mobile.domain.repository

import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface PlayoneRepository {

    fun clearPlayones(): Completable

    fun savePlayones(playone: List<Playone>): Completable

    fun getPlayones(): Single<List<Playone>>

    fun createUser(user: User): Completable

    fun getUser(email: String): Single<User>

    fun saveUser()

    fun deleteUser()

}