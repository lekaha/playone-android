package com.playone.mobile.data

import com.playone.mobile.data.mapper.PlayoneMapper
import com.playone.mobile.data.mapper.UserMapper
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneDataStore
import com.playone.mobile.data.source.PlayoneDataStoreFactory
import com.playone.mobile.data.source.PlayoneRemoteDataStore
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import com.playone.mobile.domain.repository.PlayoneRepository
import io.reactivex.Single

/**
 * Provides an implementation of the [PlayoneDataRepository] interface for communicating to and from
 * data sources
 */
class PlayoneDataRepository constructor(
    private val factory: PlayoneDataStoreFactory,
    private val playoneMapper: PlayoneMapper,
    private val userMapper: UserMapper
) : PlayoneRepository {

    override fun clearPlayoneList() = factory.getCacheDataStore().clearPlayoneList()

    override fun savePlayoneList(playoneList: List<Playone>) =
        factory.getCacheDataStore().savePlayoneList(playoneList.map(playoneMapper::mapToEntity))

    override fun getPlayoneList(userId: Int) = factory.getRemoteDataStore().run {
        getPlayoneList(userId)
            .flatMap { playoneList ->
                (this as? PlayoneRemoteDataStore)?.savePlayoneList(playoneList)
                ?: Single.just(playoneList)
            }
            .map { it.map(playoneMapper::mapFromEntity) }
    }

    override fun clearJoinedPlayoneList() = factory.getCacheDataStore().clearJoinedPlayoneList()

    override fun saveJoinedPlayoneList(playoneList: List<Playone>) =
        factory.getCacheDataStore()
            .saveJoinedPlayoneList(playoneList.map(playoneMapper::mapToEntity))

    override fun getJoinedPlayoneList(userId: Int) = factory.obtainDataStore().run {
        getJoinedPlayoneList(userId)
            .flatMap { playoneList ->
                (this as? PlayoneRemoteDataStore)?.saveFavoritePlayoneList(playoneList)
                ?: Single.just(playoneList)
            }
            .map { it.map(playoneMapper::mapFromEntity) }
    }

    override fun clearFavoritePlayoneList() = factory.getCacheDataStore().clearFavoritePlayoneList()

    override fun saveFavoritePlayoneList(playoneList: List<Playone>) =
        factory.getCacheDataStore()
            .saveFavoritePlayoneList(playoneList.map(playoneMapper::mapToEntity))

    override fun getFavoritePlayoneList(userId: Int) = factory.obtainDataStore().run {
        getFavoritePlayoneList(userId)
            .flatMap { playoneList ->
                (this as? PlayoneRemoteDataStore)?.saveFavoritePlayoneList(playoneList)
                ?: Single.just(playoneList)
            }
            .map { it.map(playoneMapper::mapFromEntity) }
    }

    override fun clearPlayoneDetail() = factory.getCacheDataStore().clearPlayoneDetail()

    override fun savePlayoneDetail(playone: Playone) =
        factory.getCacheDataStore()
            .savePlayoneDetail(playoneMapper.mapToEntity(playone))

    override fun getPlayoneDetail(playoneId: Int) = factory.obtainDataStore().run {
        getPlayoneDetail(playoneId)
            .flatMap { entity ->
                (this as? PlayoneRemoteDataStore)?.savePlayoneDetail(entity) ?: Single.just(entity)
            }
            .map(playoneMapper::mapFromEntity)
    }

    override fun clearUser(user: User) =
        factory.getCacheDataStore().clearUserEntity(userMapper.mapToEntity(user))

    override fun saveUser(user: User) =
        factory.getCacheDataStore().saveUserEntity(userMapper.mapToEntity(user))

    override fun createUser(user: User) =
        factory.getRemoteDataStore().createUserEntity(userMapper.mapToEntity(user)).toCompletable()

    override fun getUserById(userId: Int) = factory.obtainDataStore().run {
        getUserEntityById(userId).cacheUserEntity(this)
    }

    override fun getUserByEmail(email: String) = factory.obtainDataStore().run {
        getUserEntityByEmail(email).cacheUserEntity(this)
    }

    private fun Single<UserEntity>.cacheUserEntity(dataStore: PlayoneDataStore) =
        flatMap {
            (dataStore as? PlayoneRemoteDataStore)?.saveUserEntity(it) ?: Single.just(it)
        }.map(userMapper::mapFromEntity)

}