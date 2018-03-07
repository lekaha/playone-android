package com.playone.mobile.remote.mapper

import com.playone.mobile.remote.test.factory.Factory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.modelmapper.ModelMapper

@RunWith(JUnit4::class)
class ExtensionTest {

    private lateinit var playoneEntityMapper: PlayoneEntityMapper
    private lateinit var userEntityMapper: UserEntityMapper

    @Before
    fun setup() {

        playoneEntityMapper = PlayoneEntityMapper(ModelMapper())
        userEntityMapper = UserEntityMapper(ModelMapper())
    }

    @Test
    fun extPlayoneEntityToModel() {

        val entity = Factory.makePlayoneEntity()
        val model = entity.toModel(playoneEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)
    }

    @Test
    fun extPlayoneModelToEntity() {

        val model = Factory.makePlayoneModel()
        val entity = model.toEntity(playoneEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)
    }

    @Test
    fun extUserEntityToModel() {

        val entity = Factory.makeUserEntity()
        val model = entity.toModel(userEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)
    }

    @Test
    fun extUserModelToEntity() {

        val model = Factory.makeUserModel()
        val entity = model.toEntity(userEntityMapper)

        EntityMapperTest.assertTheSame(entity, model)
    }
}