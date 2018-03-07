package com.playone.mobile.cache.test.factory

import com.playone.mobile.cache.model.CachedBufferoo
import com.playone.mobile.cache.test.factory.DataFactory.Factory.randomUuid
import com.playone.mobile.data.model.BufferooEntity

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeCachedBufferoo(): CachedBufferoo {
            return CachedBufferoo().apply {
                name = randomUuid()
                title = randomUuid()
                avatar = randomUuid()
            }
        }

        fun makeBufferooEntity(): BufferooEntity {
            return BufferooEntity().apply {
                name = randomUuid()
                title = randomUuid()
                avatar = randomUuid()
            }
        }

        fun makeBufferooEntityList(count: Int): List<BufferooEntity> {
            val bufferooEntities = mutableListOf<BufferooEntity>()
            repeat(count) {
                bufferooEntities.add(makeBufferooEntity())
            }
            return bufferooEntities
        }

        fun makeCachedBufferooList(count: Int): List<CachedBufferoo> {
            val cachedBufferoos = mutableListOf<CachedBufferoo>()
            repeat(count) {
                cachedBufferoos.add(makeCachedBufferoo())
            }
            return cachedBufferoos
        }

    }

}