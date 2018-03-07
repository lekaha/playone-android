package com.playone.mobile.presentation.test.factory

import com.playone.mobile.domain.model.Bufferoo
import com.playone.mobile.presentation.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferooList(count: Int): List<Bufferoo> {
            val bufferoos = mutableListOf<Bufferoo>()
            repeat(count) {
                bufferoos.add(makeBufferooModel())
            }
            return bufferoos
        }

        fun makeBufferooModel() = Bufferoo().apply {
            name = randomUuid()
            title = randomUuid()
            avatar = randomUuid()
        }

    }

}