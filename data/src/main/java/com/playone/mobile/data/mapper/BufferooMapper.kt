package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.domain.model.Bufferoo

/**
 * Map a [BufferooEntity] to and from a [Bufferoo] instance when data is moving between
 * this later and the Domain layer
 */
open class BufferooMapper : Mapper<BufferooEntity, Bufferoo> {

    /**
     * Map a [BufferooEntity] instance to a [Bufferoo] instance
     */
    override fun mapFromEntity(type: BufferooEntity) = Bufferoo(type.name, type.title, type.avatar)

    /**
     * Map a [Bufferoo] instance to a [BufferooEntity] instance
     */
    override fun mapToEntity(type: Bufferoo) = BufferooEntity(type.name, type.title, type.avatar)

}