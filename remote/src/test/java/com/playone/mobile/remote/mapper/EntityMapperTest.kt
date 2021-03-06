package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import com.playone.mobile.remote.test.factory.Factory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.modelmapper.ModelMapper
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class EntityMapperTest {

    companion object {

        fun assertTheSame(entity: PlayoneEntity.Entity, model: PlayoneModel) {

            assertEquals(entity.id, model.id)
            assertEquals(entity.name, model.name)
//            assertEquals(entity.description, model.description)
            assertEquals(entity.date, model.date)
            assertEquals(entity.updated, model.updated)
            assertEquals(entity.address, model.address)
            assertEquals(entity.longitude, model.longitude)
            assertEquals(entity.latitude, model.latitude)
            assertEquals(entity.limit, model.limit)
            assertEquals(entity.level, model.level)
            assertEquals(entity.host, model.host)
            assertEquals(entity.userId, model.userId)
        }

        fun assertTheSame(entity: UserEntity, model: UserModel) {

            assertEquals(entity.id, model.id)
            assertEquals(entity.name, model.name)
            assertEquals(entity.email, model.email)
            assertEquals(entity.pictureURL, model.pictureURL)
//            assertEquals(entity.description, model.description)
//            assertEquals(entity.grade, model.grade)
            assertEquals(entity.deviceToken, model.deviceToken)
            assertEquals(entity.age, model.age)
            assertEquals(entity.level, model.level)
            assertEquals(entity.years, model.years)
            assertEquals(entity.teams, model.teams)
        }
    }

    private lateinit var playoneEntityMapper: PlayoneEntityMapper
    private lateinit var userEntityMapper: UserEntityMapper

    @Before
    fun setup() {

        playoneEntityMapper = PlayoneEntityMapper(ModelMapper())
        userEntityMapper = UserEntityMapper(ModelMapper())
    }

    @Test
    fun playoneMapFromRemoteToData() {

        val model = Factory.makePlayoneModel()
        val entity = playoneEntityMapper.mapToData(model)

        assertTheSame(entity, model)
    }

    @Test
    fun playoneMapFromDataToRemote() {

        val entity = Factory.makePlayoneEntity()
        val model = playoneEntityMapper.mapFromData(entity)

        assertTheSame(entity, model)
    }

    @Test
    fun userMapFromRemoteToData() {

        val model = Factory.makeUserModel()
        val entity = userEntityMapper.mapToData(model)

        assertTheSame(entity, model)
    }

    @Test
    fun userMapFromDataToRemote() {

        val entity = Factory.makeUserEntity()
        val model = userEntityMapper.mapFromData(entity)

        assertTheSame(entity, model)
    }
}