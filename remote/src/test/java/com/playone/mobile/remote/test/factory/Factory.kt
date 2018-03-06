package com.playone.mobile.remote.test.factory

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

object Factory {

    fun makePlayoneModel() = PlayoneModel(DataFactory.randomUuid(),
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

    fun makeUserModel() = UserModel(DataFactory.randomUuid(),
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
}