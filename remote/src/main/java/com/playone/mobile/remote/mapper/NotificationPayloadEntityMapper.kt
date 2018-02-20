package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.remote.model.NotificationPayloadModel

/**
 * Map a [NotificationPayloadModel] to and from a [NotificationPayloadEntity] instance when
 * data is moving between this layer and the data layer.
 */
class NotificationPayloadEntityMapper : EntityMapper<NotificationPayloadModel, NotificationPayloadEntity> {
    override fun mapToData(type: NotificationPayloadModel) = type.run {
        NotificationPayloadEntity(groupId, senderToken, receiverToken, teamId, groupId, msg)
    }

    override fun mapFromData(type: NotificationPayloadEntity) = type.run {
        NotificationPayloadModel(groupId, senderToken, receiverToken, teamId, groupId, msg)
    }
}