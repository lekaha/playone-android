package com.playone.mobile.remote.test.factory

import com.playone.mobile.data.test.factory.DataFactory
import com.playone.mobile.remote.BufferooService
import com.playone.mobile.remote.model.BufferooModel

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferooResponse(): BufferooService.BufferooResponse {

            val bufferooResponse = BufferooService.BufferooResponse()

            bufferooResponse.team = makeBufferooModelList(5)

            return bufferooResponse
        }

        fun makeBufferooModelList(count: Int): List<BufferooModel> {

            val bufferooEntities = mutableListOf<BufferooModel>()

            repeat(count) {
                bufferooEntities.add(makeBufferooModel())
            }

            return bufferooEntities
        }

        fun makeBufferooModel() = BufferooModel().apply {
            name = DataFactory.randomUuid()
            title = DataFactory.randomUuid()
            avatar = DataFactory.randomUuid()
        }
    }
}