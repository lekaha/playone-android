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
    override fun mapFromRemote(type: BufferooModel): BufferooEntity {
        return BufferooEntity(type.name, type.title, type.avatar)
    }

}