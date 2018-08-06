package com.playone.mobile.data

import com.playone.mobile.data.mapper.Mapper
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.source.PlayoneDataStoreFactory
import com.playone.mobile.domain.model.Join
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import com.playone.mobile.domain.repository.PlayoneRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.IllegalArgumentException

/**
 * Provides an implementation of the [PlayoneDataRepository] interface for communicating to and from
 * data sources
 */
class PlayoneDataRepository constructor(
    private val factory: PlayoneDataStoreFactory,
    private val playoneMapper: Mapper<PlayoneEntity, Playone>,
    private val userMapper: Mapper<UserEntity, User>
) : PlayoneRepository {

    override fun createPlayone(playone: Playone): Single<Playone> {
        if (playone !is Playone.CreateParameters) {
            throw IllegalArgumentException("Not Playone for creation")
        }

        return factory.getRemoteDataStore().createPlayoneDetail(
                    playone.myUserId,
                    playoneMapper.mapToEntity(playone)
                ).map(playoneMapper::mapFromEntity)
    }

    override fun updatePlayone(playone: Playone): Single<Playone> {
        if (playone !is Playone.UpdateParameters) {
            throw IllegalArgumentException("Not Playone for updating")
        }

        return factory.getRemoteDataStore().updatePlayoneDetail(
                playone.myUserId,
                playoneMapper.mapToEntity(playone)
        ).map(playoneMapper::mapFromEntity)
    }

    override fun joinPlayone(join: Join): Completable {
        if (join !is Join.Request) {
            throw IllegalArgumentException("Not Join for request")
        }

        return factory.getRemoteDataStore().joinPlayone(
                join.myUserId,
                join.joinPlayoneId,
                join.message
        )
    }

    override fun favoritePlayone(playoneId: String, userId: String, isFavorite: Boolean) =
        factory.obtainDataStore().favoritePlayone(playoneId, userId, isFavorite)

    override fun isFavorite(playoneId: String, userId: String) =
        factory.obtainDataStore().isFavorite(playoneId, userId)

    override fun isJoined(playoneId: String, userId: String) =
        factory.getRemoteDataStore().isJoined(playoneId, userId)

    override fun createUser(user: User): Single<User> =
        factory.getRemoteDataStore().createUser(
            userMapper.mapToEntity(user)).map(userMapper::mapFromEntity)

    override fun updateUser(userId: String, user: User): Single<User> =
        factory.obtainDataStore().updateUser(
            userId,
            userMapper.mapToEntity(user)
        ).map(userMapper::mapFromEntity)

    override fun getUserByEmail(email: String): Single<User> =
        factory.getRemoteDataStore().fetchUserByEmail(email).map(userMapper::mapFromEntity)

    override fun getUserById(userId: String): Single<User> =
        factory.getRemoteDataStore().fetchUserById(userId).map(userMapper::mapFromEntity)

    override fun getPlayoneDetail(userId: String, playoneId: String): Single<Playone> =
        isFavorite(playoneId, userId).flatMap { favorited ->
            factory.obtainDataStore().fetchPlayoneDetail(userId, playoneId)
                .map {
                    playoneMapper.mapFromEntity(it)
                        .takeIf { it is Playone.Detail }
                        .let { it as Playone.Detail }
                        .apply { isFavorited = favorited }
                }
        }


    override fun getFavoritePlayoneList(userId: String): Single<List<Playone>> =
        factory.obtainDataStore().fetchFavoritePlayoneList(userId).map {
            it.map(playoneMapper::mapFromEntity)
        }

    override fun getJoinedPlayoneList(userId: String): Single<List<Playone>> =
        factory.obtainDataStore().fetchJoinedPlayoneList(userId).map {
            it.map(playoneMapper::mapFromEntity)
        }

    override fun getPlayoneList(userId: String): Single<List<Playone>> =
        factory.obtainDataStore().fetchAllPlayoneList(userId).map {
            it.map(playoneMapper::mapFromEntity)
        }

    override fun getOwnPlayoneList(userId: String): Single<List<Playone>> =
        factory.obtainDataStore().fetchPlayoneList(userId).map {
            it.map(playoneMapper::mapFromEntity)
        }
}