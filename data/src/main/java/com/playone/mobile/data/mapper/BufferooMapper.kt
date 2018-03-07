package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.domain.model.Bufferoo
import org.modelmapper.ModelMapper

/**
 * Map a [BufferooEntity] to and from a [Bufferoo] instance when data is moving between
 * this later and the Domain layer
 */
open class BufferooMapper(
    private val modelMapper: ModelMapper
) : Mapper<BufferooEntity, Bufferoo> {

    /**
     * Map a [BufferooEntity] instance to a [Bufferoo] instance
     */
    override fun mapFromEntity(type: BufferooEntity) =
        modelMapper.map(type, Bufferoo::class.java)

    /**
     * Map a [Bufferoo] instance to a [BufferooEntity] instance
     */
    override fun mapToEntity(type: Bufferoo) =
        modelMapper.map(type, BufferooEntity::class.java)

}