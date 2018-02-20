@file:Suppress("NOTHING_TO_INLINE")

package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.NotificationPayloadModel
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

inline fun PlayoneEntity.toModel(mapper: PlayoneEntityMapper) = mapper.mapFromData(this)

inline fun PlayoneModel.toEntity(mapper: PlayoneEntityMapper) = mapper.mapToData(this)

inline fun UserEntity.toModel(mapper: UserEntityMapper) = mapper.mapFromData(this)

inline fun UserModel.toEntity(mapper: UserEntityMapper) = mapper.mapToData(this)

inline fun NotificationPayloadEntity.toModel(mapper: NotificationPayloadEntityMapper) =
    mapper.mapFromData(this)

inline fun NotificationPayloadModel.toEntity(mapper: NotificationPayloadEntityMapper) =
    mapper.mapToData(this)
