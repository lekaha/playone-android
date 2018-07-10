package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.domain.model.Playone
import org.modelmapper.ModelMapper

/**
 * Map a [PlayoneEntity] to and from a [Playone] instance when data is moving between
 * this later and the Domain layer.
 */
open class PlayoneMapper(
    private val modelMapper: ModelMapper
) : Mapper<PlayoneEntity, Playone> {

    /**
     * Map a [PlayoneEntity] instance to a [Playone] instance.
     */
    override fun mapFromEntity(type: PlayoneEntity): Playone {
        if (type !is PlayoneEntity.Entity) {
            throw IllegalArgumentException("Data type is wrong!!!")
        }

        return modelMapper.map(type, Playone.Detail::class.java)
    }

    private fun mapToPlayoneEntityForCreate(playone: Playone.CreateParameters)
        : PlayoneEntity.Create = PlayoneEntity.Create(
        playone.name,
        playone.description,
        playone.playoneDate.time,
        playone.address,
        playone.longitude,
        playone.latitude,
        playone.limitPeople,
        playone.level,
        playone.myUserId,
        playone.coverImagePath
    )

    private fun mapToPlayoneEntity(playone: Playone.Detail): PlayoneEntity.Entity =
        PlayoneEntity.Entity(
            playone.id,
            playone.name,
            playone.description,
            playone.date,
            playone.updated,
            playone.address,
            playone.longitude,
            playone.latitude,
            playone.limit,
            playone.level,
            playone.host,
            playone.userId,
            playone.coverUrl)

    /**
     * Map a [Playone] instance to a [PlayoneEntity] instance.
     */
    override fun mapToEntity(type: Playone) =
        when (type) {
            is Playone.CreateParameters -> mapToPlayoneEntityForCreate(type)
            is Playone.Detail -> mapToPlayoneEntity(type)
            else -> throw IllegalArgumentException("Data type is wrong!!!")
        }
}