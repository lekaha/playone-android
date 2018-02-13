package com.playone.android.ui.test.factory

import com.playone.android.domain.model.Bufferoo

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

        fun makeBufferooModel(): Bufferoo {
            return Bufferoo(DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid())
        }

    }

}