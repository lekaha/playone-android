package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.test.factory.Factory
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.modelmapper.ModelMapper
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class EntityMapperTest {

    companion object {

        fun assertTheSame(entity: PlayoneEntity, playone: Playone) {

            assertEquals(entity.id, playone.id)
            assertEquals(entity.name, playone.name)
            assertEquals(entity.description, playone.description)
            assertEquals(entity.date, playone.date)
            assertEquals(entity.updated, playone.updated)
            assertEquals(entity.address, playone.address)
            assertEquals(entity.longitude, playone.longitude)
            assertEquals(entity.latitude, playone.latitude)
            assertEquals(entity.limit, playone.limit)
            assertEquals(entity.level, playone.level)
            assertEquals(entity.host, playone.host)
            assertEquals(entity.userId, playone.userId)
        }

        fun assertTheSame(entity: UserEntity, user: User) {

            assertEquals(entity.id, user.id)
            assertEquals(entity.name, user.name)
            assertEquals(entity.email, user.email)
            assertEquals(entity.pictureURL, user.pictureURL)
            assertEquals(entity.description, user.description)
            assertEquals(entity.grade, user.grade)
            assertEquals(entity.deviceToken, user.deviceToken)
            assertEquals(entity.age, user.age)
            assertEquals(entity.level, user.level)
            assertEquals(entity.years, user.years)
            assertEquals(entity.teams, user.teams)
        }
    }

    private lateinit var playoneMapper: PlayoneMapper
    private lateinit var userMapper: UserMapper

    @Before
    fun setup() {

        playoneMapper = PlayoneMapper(ModelMapper())
        userMapper = UserMapper(ModelMapper())
    }

    @Test
    fun playoneMapToEntity() {

        val playone = Factory.makePlayone()
        val entity = playoneMapper.mapToEntity(playone)

        assertTheSame(entity, playone)
    }

    @Test
    fun playoneMapFromEntity() {

        val entity = Factory.makePlayoneEntity()
        val playone = playoneMapper.mapFromEntity(entity)

        assertTheSame(entity, playone)
    }

    @Test
    fun userMapToEntity() {

        val user = Factory.makeUser()
        val entity = userMapper.mapToEntity(user)

        assertTheSame(entity, user)
    }

    @Test
    fun userMapFromEntity() {

        val entity = Factory.makeUserEntity()
        val user = userMapper.mapFromEntity(entity)

        assertTheSame(entity, user)
    }
}