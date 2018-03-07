package com.playone.mobile.cache.mapper

import com.playone.mobile.cache.model.CachedBufferoo
import com.playone.mobile.data.model.BufferooEntity
import org.modelmapper.ModelMapper

/**
 * Map a [CachedBufferoo] instance to and from a [BufferooEntity] instance when data is moving between
 * this later and the Data layer
 */
class BufferooEntityMapper(
    private val modelMapper: ModelMapper
) : EntityMapper<CachedBufferoo, BufferooEntity> {

    /**
     * Map a [BufferooEntity] instance to a [CachedBufferoo] instance
     */
    override fun mapToCached(type: BufferooEntity) =
        modelMapper.map(type, CachedBufferoo::class.java)

    /**
     * Map a [CachedBufferoo] instance to a [BufferooEntity] instance
     */
    override fun mapFromCached(type: CachedBufferoo) =
        modelMapper.map(type, BufferooEntity::class.java)

}