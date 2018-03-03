package com.playone.mobile.data.mapper

import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.domain.model.User

/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer.
 */
open class UserMapper : Mapper<UserEntity, User> {

    /**
     * Map a [UserEntity] instance to a [User] instance.
     */
    override fun mapFromEntity(type: UserEntity) = User()

    /**
     * Map a [User] instance to a [UserEntity] instance.
     */
    override fun mapToEntity(type: User) = UserEntity()

}