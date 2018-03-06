package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.remote.model.PlayoneModel
import org.modelmapper.ModelMapper

/**
 * Map a [PlayoneModel] to and from a [PlayoneEntity] instance when data is moving between
 * this layer and the data layer.
 */
class PlayoneEntityMapper(
    private val modelMapper: ModelMapper
) : EntityMapper<PlayoneModel, PlayoneEntity> {

    override fun mapToData(type: PlayoneModel) =
        modelMapper.map(type, PlayoneEntity::class.java)

    override fun mapFromData(type: PlayoneEntity) =
        modelMapper.map(type, PlayoneModel::class.java)
}