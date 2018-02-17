package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.remote.model.NotificationPayloadModel

/**
 * Map a [NotificationPayloadModel] to and from a [NotificationPayloadEntity] instance when
 * data is moving between this layer and the data layer.
 */
class NotificationPayloadEntityMapper : EntityMapper<NotificationPayloadModel, NotificationPayloadEntity> {
    override fun mapFromRemote(type: NotificationPayloadModel) = type.run {
        NotificationPayloadEntity(groupId, senderToken, receiverToken, teamId, groupId, msg)
    }
}