package com.playone.mobile.remote.test.factory

import com.playone.mobile.remote.BufferooService
import com.playone.mobile.remote.model.BufferooModel
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.test.factory.DataFactory.Factory.randomDouble
import com.playone.mobile.remote.test.factory.DataFactory.Factory.randomInt
import com.playone.mobile.remote.test.factory.DataFactory.Factory.randomLong
import com.playone.mobile.remote.test.factory.DataFactory.Factory.randomUuid

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

        fun makeBufferooModel(): BufferooModel {
            return BufferooModel(randomUuid(), randomUuid(), randomUuid())
        }

        fun makePlayoneMode() = PlayoneModel(randomUuid(),
                                             randomUuid(),
                                             randomUuid(),
                                             randomLong(),
                                             randomLong(),
                                             randomUuid(),
                                             randomDouble(),
                                             randomDouble(),
                                             randomInt(),
                                             randomInt(),
                                             randomUuid(),
                                             randomUuid())

    }

}