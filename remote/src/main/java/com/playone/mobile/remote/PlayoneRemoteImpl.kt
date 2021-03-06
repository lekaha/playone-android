package com.playone.mobile.remote

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.remote.bridge.playone.PlayoneService
import com.playone.mobile.remote.mapper.EntityMapper
import com.playone.mobile.remote.mapper.toModel
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Remote implementation for retrieving Playone instances. This class implements the
 * [com.playone.mobile.data.repository.PlayoneRemote] from the Data layer as it is that
 * layers responsibility for defining the operations in which data store implementation
 * layers can carry out.
 */
class PlayoneRemoteImpl constructor(
    private val service: PlayoneService,
    private val playoneMapper: EntityMapper<PlayoneModel, PlayoneEntity>,
    private val userMapper: EntityMapper<UserModel, UserEntity>
) : PlayoneRemote {
    override fun sendJoinPlayoneRequest(userId: String, playoneId: String, msg: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun responseJoinPlayoneRequest(playoneId: String, accept: Boolean, msg: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isJoined(playoneId: String, userId: String): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity)
        : Single<PlayoneEntity> =
        service.createPlayoneDetail(userId,
                playoneEntity.toModel(playoneMapper))
                .map(playoneMapper::mapToData)

    override fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity)
        : Single<PlayoneEntity> =
        service.updatePlayoneDetail(userId, playoneEntity.toModel(playoneMapper))
                .map(playoneMapper::mapToData)

    override fun deletePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchPlayoneDetail(userId: String, playoneId: String): Single<PlayoneEntity> =
        service.retrievePlayoneDetail(playoneId).map(playoneMapper::mapToData)

    override fun fetchPlayoneList(userId: String): Single<List<PlayoneEntity>> =
        service.retrievePlayoneList(userId).mapPlayoneToEntity()

    override fun fetchFavoritePlayoneList(userId: String): Single<List<PlayoneEntity>> =
        service.retrieveFavoritePlayoneList(userId).mapPlayoneToEntity()

    override fun fetchJoinedPlayoneList(userId: String): Single<List<PlayoneEntity>> =
        service.retrieveJoinedPlayoneList(userId).mapPlayoneToEntity()

    override fun fetchAllPlayoneList(userId: String): Single<List<PlayoneEntity>> =
        service.retrievePlayoneList().mapPlayoneToEntity()

    override fun createUser(userEntity: UserEntity): Single<UserEntity> =
        service.createUser(userEntity.toModel(userMapper)).mapUserToEntity()

    override fun updateUser(userId: String, userEntity: UserEntity): Single<UserEntity> =
        userEntity.toModel(userMapper).apply {
            id = userId
        }.let {
            service.updateUser(it).mapUserToEntity()
        }

    override fun deleteUser(userEntity: UserEntity): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUserByEmail(email: String) =
        service.retrieveUserModelByEmail(email).mapUserToEntity()

    override fun fetchUserById(userId: String) =
        service.retrieveUserModel(userId).mapUserToEntity()

    override fun toggleFavorite(playoneId: String, userId: String) =
        service.toggleFavorite(playoneId, userId)

    override fun isFavorited(playoneId: String, userId: String) =
        service.isFavorite(playoneId, userId)

    private fun Single<List<PlayoneModel>>.mapPlayoneToEntity() =
        map { it.map(playoneMapper::mapToData) }

    private fun Single<UserModel>.mapUserToEntity() = map(userMapper::mapToData)
}
