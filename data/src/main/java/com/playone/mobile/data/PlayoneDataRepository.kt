package com.playone.mobile.data

import com.playone.mobile.data.mapper.Mapper
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.source.PlayoneDataStoreFactory
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
    private val playoneMapper: Mapper<PlayoneEntity, Playone>,
    private val userMapper: Mapper<UserEntity, User>
) : PlayoneRepository {

    override fun createPlayone(userId: String, playone: Playone): Single<Playone> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePlayone(userId: String, playone: Playone): Single<Playone> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun joinTeam(playoneId: String, userId: String, isJoin: Boolean): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendJoinRequest(playoneId: String, userId: String, msg: String): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toggleFavorite(playoneId: String, userId: String): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isFavorite(playoneId: String, userId: String): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isJoined(playoneId: String, userId: String): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByEmail(email: String): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserById(userId: String): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayoneDetail(playoneId: String): Single<Playone> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoritePlayoneList(userId: String): Single<List<Playone>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJoinedPlayoneList(userId: String): Single<List<Playone>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayoneList(userId: String): Single<List<Playone>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    fun clearPlayoneList() = factory.getCacheDataStore().clearPlayoneList()
//
//    fun savePlayoneList(playoneList: List<Playone>) =
//        factory.getCacheDataStore().savePlayoneList(playoneList.map(playoneMapper::mapToEntity))
//
//    fun clearJoinedPlayoneList() = factory.getCacheDataStore().clearJoinedPlayoneList()
//
//    fun saveJoinedPlayoneList(playoneList: List<Playone>) =
//            factory.getCacheDataStore()
//                    .saveJoinedPlayoneList(playoneList.map(playoneMapper::mapToEntity))
//
//    fun clearFavoritePlayoneList() = factory.getCacheDataStore().clearFavoritePlayoneList()
//
//    fun saveFavoritePlayoneList(playoneList: List<Playone>) =
//            factory.getCacheDataStore()
//                    .saveFavoritePlayoneList(playoneList.map(playoneMapper::mapToEntity))
//
//    fun clearPlayoneDetail() = factory.getCacheDataStore().clearPlayoneDetail()
//
//    fun savePlayoneDetail(userId: String, playone: Playone) =
//            factory.getRemoteDataStore().savePlayoneDetail(userId, playoneMapper.mapToEntity(playone))
//                    .map { playoneMapper.mapFromEntity(it) }
//
//    fun clearUser(user: User) =
//            factory.getCacheDataStore().clearUserEntity(userMapper.mapToEntity(user))
//
//    fun saveUser(user: User) =
//            factory.getCacheDataStore().saveUserEntity(userMapper.mapToEntity(user))
//
//    fun createUser(user: User) =
//            factory.getRemoteDataStore().createUser(userMapper.mapToEntity(user)).toCompletable()

//    override fun getPlayoneList() = factory.obtainDataStore().run {
//        fetchPlayoneList().flatMap { playoneList ->
//            // TODO(jieyi): 2018/03/10 Avoiding crashing becz the cache datastore we didn't implement yet.
////                (this as? PlayoneRemoteDataStore)?.savePlayoneList(playoneList)
////                ?:
//            Single.just(playoneList)
//        }
//            .map { it.map(playoneMapper::mapFromEntity) }
//    }
//
//    override fun getPlayoneList(userId: String) = factory.obtainDataStore().run {
//        fetchPlayoneList(userId)
//            .flatMap { playoneList ->
//                // TODO(jieyi): 2018/03/10 Avoiding crashing becz the cache datastore we didn't implement yet.
////                (this as? PlayoneRemoteDataStore)?.savePlayoneList(playoneList)
////                ?:
//                Single.just(playoneList)
//            }
//            .map { it.map(playoneMapper::mapFromEntity) }
//    }
//
//    override fun getJoinedPlayoneList(userId: String) = factory.obtainDataStore().run {
//        fetchJoinedPlayoneList(userId)
//            .flatMap { playoneList ->
//                (this as? PlayoneRemoteDataStore)?.saveFavoritePlayoneList(playoneList)
//                ?: Single.just(playoneList)
//            }
//            .map { it.map(playoneMapper::mapFromEntity) }
//    }
//
//    override fun getFavoritePlayoneList(userId: String) = factory.obtainDataStore().run {
//        fetchFavoritePlayoneList(userId)
//            .flatMap { playoneList ->
//                (this as? PlayoneRemoteDataStore)?.saveFavoritePlayoneList(playoneList)
//                ?: Single.just(playoneList)
//            }
//            .map { it.map(playoneMapper::mapFromEntity) }
//    }
//
//    override fun getPlayoneDetail(playoneId: String) = factory.obtainDataStore().run {
//        fetchPlayoneDetail(playoneId)
//            .flatMap { entity ->
//                    // TODO:
////                (this as? PlayoneRemoteDataStore)?.savePlayoneDetail(entity) ?: Single.just(entity)
//                Single.just(entity)
//            }
//            .map(playoneMapper::mapFromEntity)
//    }
//
//    override fun getUserById(userId: String) = factory.obtainDataStore().run {
//        fetchUserEntity(userId).cacheUserEntity(this)
//    }
//
//    override fun getUserByEmail(email: String) = factory.obtainDataStore().run {
//        fetchUserEntity(email).cacheUserEntity(this)
//    }
//
//    override fun createPlayone(userId: String, playone: Playone): Single<Playone> =
//        factory.getRemoteDataStore().createPlayoneDetail(userId, playoneMapper.mapToEntity(playone))
//            .map(playoneMapper::mapFromEntity)
//
//    override fun updatePlayone(userId: String, playone: Playone): Single<Playone> =
//        factory.getRemoteDataStore().updatePlayoneDetail(userId, playoneMapper.mapToEntity(playone))
//            .map(playoneMapper::mapFromEntity)
//
//    override fun joinTeam(playoneId: String, userId: String, isJoin: Boolean) =
//        singleBooleanRequest { joinTeamAsMember(playoneId, userId, isJoin) }
//
//    override fun sendJoinRequest(playoneId: String, userId: String, msg: String) =
//        singleBooleanRequest { sendJoinRequest(playoneId, userId, msg) }
//
//    override fun toggleFavorite(playoneId: String, userId: String) =
//        factory.getRemoteDataStore().toggleFavorite(playoneId, userId)
//
//    override fun isFavorite(playoneId: String, userId: String) =
//        factory.getRemoteDataStore().isFavorite(playoneId, userId)
//
//    override fun isJoined(playoneId: String, userId: String) =
//        factory.getRemoteDataStore().isJoined(playoneId, userId)
//
//    private fun Single<UserEntity>.cacheUserEntity(dataStore: PlayoneDataStore) =
//        flatMap {
//            (dataStore as? PlayoneRemoteDataStore)?.saveUserEntity(it) ?: Single.just(it)
//        }.map(userMapper::mapFromEntity)
//
//    private fun singleBooleanRequest(request: PlayoneDataStore.() -> Single<PlayoneRemote.Result>) =
//        factory.getRemoteDataStore().run(request).map { SUCCESS == it }
}