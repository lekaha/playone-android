package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.UserModel
import org.modelmapper.ModelMapper

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this layer and the data layer.
 */
class UserEntityMapper(
    private val modelMapper: ModelMapper
) : EntityMapper<UserModel, UserEntity> {

    override fun mapToData(type: UserModel) =
        modelMapper.map(type, UserEntity::class.java)

    override fun mapFromData(type: UserEntity) =
        modelMapper.map(type, UserModel::class.java)
}