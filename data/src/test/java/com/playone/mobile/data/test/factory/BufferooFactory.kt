package com.playone.mobile.data.test.factory

import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.data.test.factory.DataFactory.Factory.randomUuid
import com.playone.mobile.domain.model.Bufferoo

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferooEntity() = BufferooEntity().apply {
            name = randomUuid()
            title = randomUuid()
            avatar = randomUuid()
        }

        fun makeBufferoo() = Bufferoo().apply {
            name = randomUuid()
            title = randomUuid()
            avatar = randomUuid()
        }

        fun makeBufferooEntityList(count: Int): List<BufferooEntity> {
            val bufferooEntities = mutableListOf<BufferooEntity>()
            repeat(count) {
                bufferooEntities.add(makeBufferooEntity())
            }
            return bufferooEntities
        }

        fun makeBufferooList(count: Int): List<Bufferoo> {
            val bufferoos = mutableListOf<Bufferoo>()
            repeat(count) {
                bufferoos.add(makeBufferoo())
            }
            return bufferoos
        }

    }

}