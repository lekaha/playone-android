package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExtensionTest {

    private lateinit var playoneEntityMapper: PlayoneEntityMapper
    private lateinit var userEntityMapper: UserEntityMapper

    @Before
    fun setup() {

        playoneEntityMapper = PlayoneEntityMapper()
        userEntityMapper = UserEntityMapper()

    }

    @Test
    fun extPlayoneEntityToModel() {

        val entity = PlayoneEntity()
        val model = entity.toModel(playoneEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)

    }

    @Test
    fun extPlayoneModelToEntity() {

        val model = PlayoneModel()
        val entity = model.toEntity(playoneEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)

    }

    @Test
    fun extUserEntityToModel() {

        val entity = UserEntity()
        val model = entity.toModel(userEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)

    }

    @Test
    fun extUserModelToEntity() {

        val model = UserModel()
        val entity = model.toEntity(userEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)

    }

}