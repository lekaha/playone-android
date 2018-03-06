package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.remote.model.NotificationPayloadModel
import org.modelmapper.ModelMapper

/**
 * Map a [NotificationPayloadModel] to and from a [NotificationPayloadEntity] instance when
 * data is moving between this layer and the data layer.
 */
class NotificationPayloadEntityMapper(
    private val modelMapper: ModelMapper
) : EntityMapper<NotificationPayloadModel, NotificationPayloadEntity> {

    override fun mapToData(type: NotificationPayloadModel) =
        modelMapper.map(type, NotificationPayloadEntity::class.java)

    override fun mapFromData(type: NotificationPayloadEntity) =
        modelMapper.map(type, NotificationPayloadModel::class.java)
}