package com.playone.mobile.remote.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

/**
 * Map a [PlayoneModel] to and from a [PlayoneEntity] instance when data is moving between
 * this layer and the data layer.
 *
 * @author  Jieyi Wu
 * @since   2018/02/17
 */
class UserModelMapper : EntityMapper<UserModel, UserEntity> {
    override fun mapFromRemote(type: UserModel) = type.run {
        UserEntity(id, name, email, pictureURL, description, grade, age, level, years, teams)
    }
}