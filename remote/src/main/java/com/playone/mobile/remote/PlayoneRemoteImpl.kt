package com.playone.mobile.remote

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.remote.mapper.PlayoneEntityMapper
import com.playone.mobile.remote.mapper.UserEntityMapper
import com.playone.mobile.remote.mapper.toModel
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import io.reactivex.Single

/**
 * Remote implementation for retrieving Playone instances. This class implements the
 * [com.playone.mobile.data.repository.PlayoneRemote] from the Data layer as it is that
 * layers responsibility for defining the operations in which data store implementation
 * layers can carry out.
 */
class PlayoneRemoteImpl constructor(
    private val service: PlayoneService,
    private val playoneMapper: PlayoneEntityMapper,
    private val userMapper: UserEntityMapper
) : PlayoneRemote {
    override fun fetchPlayoneList(userId: Int) =
        service.fetchPlayoneList(userId).mapPlayoneToEntity()

    override fun fetchJoinedPlayoneList(userId: Int) =
        service.fetchJoinedPlayoneList(userId).mapPlayoneToEntity()

    override fun fetchFavoritePlayoneList(userId: Int) =
        service.fetchFavoritePlayoneList(userId).mapPlayoneToEntity()

    override fun fetchPlayoneDetail(playoneId: Int) =
        service.fetchPlayoneDetail(playoneId).map(playoneMapper::mapToData)

    override fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()
//        service.createPlayoneDetail(userId, playoneEntity.toModel(playoneMapper))

    override fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) = TODO()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) =
        service.toggleFavorite(playoneId, userId)

    override fun isFavorite(playoneId: Int, userId: Int) =
        service.isFavorite(playoneId, userId)

    override fun isJoint(playoneId: Int, userId: Int) =
        service.isJoint(playoneId, userId)

    //region For Auth0
    override fun userEntity(userId: Int) = service.userModel(userId).mapUserToEntity()

    override fun createUser(userEntity: UserEntity) =
        service.createUser(userEntity.toModel(userMapper)).mapUserToEntity()

    override fun updateUser(userEntity: UserEntity) =
        service.updateUser(userEntity.toModel(userMapper)).mapUserToEntity()

    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) =
        service.updateUser(userEntity.toModel(userMapper), lastDeviceToken).mapUserToEntity()
    //endregion

    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()

    private fun Single<List<PlayoneModel>>.mapPlayoneToEntity() =
        map { it.map(playoneMapper::mapToData) }

    private fun Single<UserModel>.mapUserToEntity() = map(userMapper::mapToData)
}