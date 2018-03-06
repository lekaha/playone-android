package com.playone.mobile.data.test.factory

import java.util.concurrent.ThreadLocalRandom

/**
 * Factory class for data instances
 */
class DataFactory {

    companion object Factory {

        fun randomUuid() = java.util.UUID.randomUUID().toString()

        fun randomInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

        fun randomDouble() = randomInt().toDouble() / randomInt() + randomInt()

        fun randomLong() = randomInt().toLong()

        fun randomBoolean() = Math.random() < 0.5

        fun makeStringList(count: Int): List<String> {

            val items: MutableList<String> = mutableListOf()

            repeat(count) {
                items.add(randomUuid())
            }

            return items
        }

        fun makeHashMap(count: Int) = hashMapOf<String, Any>().apply {
            repeat(count) { put(randomUuid(), randomUuid()) }
        }
    }
}