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
        PlayoneEntity.Entity(
            type.id,
            type.name,
            type.description,
            type.date,
            type.updated,
            type.address,
            type.longitude,
            type.latitude,
            type.limit,
            type.level,
            type.host,
            type.userId,
            type.cover)

    private fun mapFromPlayoneEntityForCreate(type: PlayoneEntity.Create) =
        PlayoneModel(
            _name = type.name,
            _address =  type.address,
            _date =  type.date,
            _description = type.description,
            _host = type.host,
            _latitude = type.latitude,
            _longitude = type.longitude,
            _level = type.level,
            _limit = type.limit,
            _cover = type.coverPath
        )

    private fun mapFromPlayoneEntity(type: PlayoneEntity.Entity) =
        PlayoneModel(
            _id = type.id,
            _name = type.name,
            _address =  type.address,
            _date =  type.date,
            _description = type.description,
            _host = type.host,
            _latitude = type.latitude,
            _longitude = type.longitude,
            _level = type.level,
            _limit = type.limit,
            _updated = type.updated,
            _userId = type.userId,
            _cover = type.coverUrl
        )

    override fun mapFromData(type: PlayoneEntity): PlayoneModel  =
        when(type) {
            is PlayoneEntity.Create -> mapFromPlayoneEntityForCreate(type)
            is PlayoneEntity.Entity -> mapFromPlayoneEntity(type)
            else -> throw IllegalArgumentException("Data type is wrong!!!")
        }
}