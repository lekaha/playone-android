package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.domain.model.Playone

/**
 * Map a [PlayoneEntity] to and from a [Playone] instance when data is moving between
 * this later and the Domain layer.
 */
open class PlayoneMapper : Mapper<PlayoneEntity, Playone> {

    /**
     * Map a [PlayoneEntity] instance to a [Playone] instance.
     */
    override fun mapFromEntity(type: PlayoneEntity) = Playone()

    /**
     * Map a [Playone] instance to a [PlayoneEntity] instance.
     */
    override fun mapToEntity(type: Playone) = PlayoneEntity()

}