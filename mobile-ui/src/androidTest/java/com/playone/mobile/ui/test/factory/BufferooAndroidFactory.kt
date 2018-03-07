package com.playone.mobile.ui.test.factory

import com.playone.mobile.data.test.factory.DataFactory
import com.playone.mobile.domain.model.Bufferoo

/**
 * Factory class for Bufferoo related instances
 */
class BufferooAndroidFactory {

    companion object Factory {

        fun makeBufferooList(count: Int): List<Bufferoo> {
            val bufferoos = mutableListOf<Bufferoo>()
            repeat(count) {
                bufferoos.add(makeBufferooModel())
            }
            return bufferoos
        }

        fun makeBufferooModel() = Bufferoo().apply {
            name = DataFactory.randomUuid()
            title = DataFactory.randomUuid()
            avatar = DataFactory.randomUuid()
        }

    }

}