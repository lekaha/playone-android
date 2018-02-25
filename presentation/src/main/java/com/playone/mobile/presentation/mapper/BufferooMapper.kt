package com.playone.mobile.presentation.mapper

import com.playone.mobile.domain.model.Bufferoo
import com.playone.mobile.presentation.model.BufferooView

/**
 * Map a [BufferooView] to and from a [Bufferoo] instance when data is moving between
 * this layer and the Domain layer
 */
open class BufferooMapper : Mapper<BufferooView, Bufferoo> {

    /**
     * Map a [Bufferoo] instance to a [BufferooView] instance
     */
    override fun mapToView(type: Bufferoo) = BufferooView(type.name, type.title, type.avatar)


}