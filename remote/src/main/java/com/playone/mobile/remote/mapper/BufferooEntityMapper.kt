package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.remote.model.BufferooModel
import javax.inject.Inject

/**
 * Map a [BufferooModel] to and from a [BufferooEntity] instance when data is moving between
 * this later and the Data layer
 */
open class BufferooEntityMapper @Inject constructor(): EntityMapper<BufferooModel, BufferooEntity> {

    /**
     * Map an instance of a [BufferooModel] to a [BufferooEntity] model
     */
    override fun mapToData(type: BufferooModel) =
        BufferooEntity(type.name, type.title, type.avatar)

    override fun mapFromData(type: BufferooEntity) = TODO()
}