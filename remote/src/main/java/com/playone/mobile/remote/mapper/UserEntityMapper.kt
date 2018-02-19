package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.UserModel

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this layer and the data layer.
 */
class UserEntityMapper : EntityMapper<UserModel, UserEntity> {
    override fun mapToData(type: UserModel) = type.run {
        UserEntity(id, name, email, pictureURL, description, grade, age, level, years, teams)
    }

    override fun mapFromData(type: UserEntity) = type.run {
        UserModel(id, name, email, pictureURL, description, grade, age, level, years, teams)
    }
}