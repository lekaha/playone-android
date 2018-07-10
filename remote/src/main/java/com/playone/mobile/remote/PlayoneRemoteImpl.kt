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
            service.retrievePlayoneList().mapPlayoneToEntity()

    override fun fetchFavoritePlayoneList(userId: String): Single<List<PlayoneEntity>> =
            service.retrieveFavoritePlayoneList(userId).mapPlayoneToEntity()

    override fun fetchJoinedPlayoneList(userId: String): Single<List<PlayoneEntity>> =
            service.retrievePlayoneList(userId).mapPlayoneToEntity()

    override fun createUser(userEntity: UserEntity): Single<UserEntity> =
            service.createUser(userEntity.toModel(userMapper)).mapUserToEntity()

    override fun updateUser(userEntity: UserEntity): Single<UserEntity> =
            service.updateUser(userEntity.toModel(userMapper)).mapUserToEntity()

    override fun deleteUser(userEntity: UserEntity): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUserByEmail(email: String): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toggleFavorite(playoneId: String, userId: String) =
        service.toggleFavorite(playoneId, userId)

    override fun isFavorited(playoneId: String, userId: String) =
        service.isFavorite(playoneId, userId)

    //    override fun fetchPlayoneList() = service.retrievePlayoneList().mapPlayoneToEntity()
//
//    override fun fetchPlayoneList(userId: String) =
//        service.retrievePlayoneList(userId).mapPlayoneToEntity()
//
//    override fun fetchJoinedPlayoneList(userId: String) =
//        service.retrieveJoinedPlayoneList(userId).mapPlayoneToEntity()
//
//    override fun fetchFavoritePlayoneList(userId: String) =
//        service.retrieveFavoritePlayoneList(userId).mapPlayoneToEntity()
//
//    override fun fetchPlayoneDetail(playoneId: String) =
//        service.retrievePlayoneDetail(playoneId).map(playoneMapper::mapToData)
//
//
//    override fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
//        service.updatePlayoneDetail(userId, playoneEntity.toModel(playoneMapper))
//            .map(playoneMapper::mapToData)
//
//    override fun joinTeamAsMember(playoneId: String, userId: String, isJoin: Boolean) =
//        service.joinTeamAsMember(playoneId, userId, isJoin)
//
//    override fun sendJoinRequest(playoneId: String, userId: String, msg: String) =
//        service.sendJoinRequest(playoneId, userId, msg)
//
//    override fun toggleFavorite(playoneId: String, userId: String) =
//        service.toggleFavorite(playoneId, userId)
//
//    override fun isFavorite(playoneId: String, userId: String) =
//        service.isFavorite(playoneId, userId)
//
//    override fun isJoined(playoneId: String, userId: String) =
//        service.isJoined(playoneId, userId)
//
//    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()
//
//    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()

    private fun Single<List<PlayoneModel>>.mapPlayoneToEntity() =
        map { it.map(playoneMapper::mapToData) }

    private fun Single<UserModel>.mapUserToEntity() = map(userMapper::mapToData)
}
