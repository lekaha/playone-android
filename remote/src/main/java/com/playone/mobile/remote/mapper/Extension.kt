package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.NotificationPayloadModel
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

inline fun PlayoneEntity.toModel(mapper: EntityMapper<PlayoneModel, PlayoneEntity>) =
    mapper.mapFromData(this)

inline fun PlayoneModel.toEntity(mapper: EntityMapper<PlayoneModel, PlayoneEntity>) =
    mapper.mapToData(this)

inline fun UserEntity.toModel(mapper: EntityMapper<UserModel, UserEntity>) =
    mapper.mapFromData(this)

inline fun UserModel.toEntity(mapper: EntityMapper<UserModel, UserEntity>) =
    mapper.mapToData(this)

inline fun NotificationPayloadEntity.toModel(
    mapper: EntityMapper<NotificationPayloadModel, NotificationPayloadEntity>
) = mapper.mapFromData(this)

inline fun NotificationPayloadModel.toEntity(
    mapper: EntityMapper<NotificationPayloadModel, NotificationPayloadEntity>
) = mapper.mapToData(this)
