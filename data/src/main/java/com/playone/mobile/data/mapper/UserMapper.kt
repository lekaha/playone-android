package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.domain.model.User
import org.modelmapper.ModelMapper

/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer.
 */
open class UserMapper(
    private val modelMapper: ModelMapper
) : Mapper<UserEntity, User> {

    /**
     * Map a [UserEntity] instance to a [User] instance.
     */
    override fun mapFromEntity(type: UserEntity) =
        User().apply {
            id = type.id
            email = type.email
            name = type.name
            pictureURL = type.pictureURL
//            description = type.description.orEmpty()
//            grade = type.grade.orEmpty()
            age = type.age
            deviceToken = type.deviceToken
            level = type.level
            years = type.years
            teams = type.teams
        }

    /**
     * Map a [User] instance to a [UserEntity] instance.
     */
    override fun mapToEntity(type: User) =
        modelMapper.map(type, UserEntity::class.java)
}