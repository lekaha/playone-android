package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.remote.model.PlayoneModel

/**
 * Map a [PlayoneModel] to and from a [PlayoneEntity] instance when data is moving between
 * this layer and the data layer.
 */
class PlayoneEntityMapper : EntityMapper<PlayoneModel, PlayoneEntity> {
    override fun mapFromRemote(type: PlayoneModel) = type.run {
        PlayoneEntity(id,
                      name,
                      description,
                      date,
                      updated,
                      address,
                      longitude,
                      latitude,
                      limit,
                      level,
                      host,
                      userId)
    }
}