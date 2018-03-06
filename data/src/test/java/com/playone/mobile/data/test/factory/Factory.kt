package com.playone.mobile.data.test.factory

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User

object Factory {

    fun makePlayone() = Playone(DataFactory.randomUuid(),
                                DataFactory.randomUuid(),
                                DataFactory.randomUuid(),
                                DataFactory.randomLong(),
                                DataFactory.randomLong(),
                                DataFactory.randomUuid(),
                                DataFactory.randomDouble(),
                                DataFactory.randomDouble(),
                                DataFactory.randomInt(),
                                DataFactory.randomInt(),
                                DataFactory.randomUuid(),
                                DataFactory.randomUuid())

    fun makeUser() = User(DataFactory.randomUuid(),
                          DataFactory.randomUuid(),
                          DataFactory.randomUuid(),
                          DataFactory.randomUuid(),
                          DataFactory.randomUuid(),
                          DataFactory.randomUuid(),
                          DataFactory.randomUuid(),
                          DataFactory.randomInt(),
                          DataFactory.randomInt(),
                          DataFactory.randomInt(),
                          DataFactory.makeHashMap(30))

    fun makePlayoneEntity() = PlayoneEntity(DataFactory.randomUuid(),
                                            DataFactory.randomUuid(),
                                            DataFactory.randomUuid(),
                                            DataFactory.randomLong(),
                                            DataFactory.randomLong(),
                                            DataFactory.randomUuid(),
                                            DataFactory.randomDouble(),
                                            DataFactory.randomDouble(),
                                            DataFactory.randomInt(),
                                            DataFactory.randomInt(),
                                            DataFactory.randomUuid(),
                                            DataFactory.randomUuid())

    fun makeUserEntity() = UserEntity(DataFactory.randomUuid(),
                                      DataFactory.randomUuid(),
                                      DataFactory.randomUuid(),
                                      DataFactory.randomUuid(),
                                      DataFactory.randomUuid(),
                                      DataFactory.randomUuid(),
                                      DataFactory.randomUuid(),
                                      DataFactory.randomInt(),
                                      DataFactory.randomInt(),
                                      DataFactory.randomInt(),
                                      DataFactory.makeHashMap(30))

    fun makePlayoneList(count: Int) = mutableListOf<PlayoneEntity>().apply {
        repeat(count) { add(makePlayoneEntity()) }
    }
}