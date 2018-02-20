package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.NotificationPayloadModel
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

fun PlayoneEntity.toModel(mapper: PlayoneEntityMapper) = mapper.mapFromData(this)

fun PlayoneModel.toEntity(mapper: PlayoneEntityMapper) = mapper.mapToData(this)

fun UserEntity.toModel(mapper: UserEntityMapper) = mapper.mapFromData(this)

fun UserModel.toEntity(mapper: UserEntityMapper) = mapper.mapToData(this)

fun NotificationPayloadEntity.toModel(mapper: NotificationPayloadEntityMapper) =
    mapper.mapFromData(this)

fun NotificationPayloadModel.toEntity(mapper: NotificationPayloadEntityMapper) =
    mapper.mapToData(this)
