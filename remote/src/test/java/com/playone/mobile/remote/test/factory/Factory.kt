package com.playone.mobile.remote.test.factory

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

object Factory {

    fun makePlayoneModel() = PlayoneModel().apply {
        id = DataFactory.randomUuid()
        name = DataFactory.randomUuid()
        description = DataFactory.randomUuid()
        date = DataFactory.randomLong()
        updated = DataFactory.randomLong()
        address = DataFactory.randomUuid()
        longitude = DataFactory.randomDouble()
        latitude = DataFactory.randomDouble()
        limit = DataFactory.randomInt()
        level = DataFactory.randomInt()
        host = DataFactory.randomUuid()
        userId = DataFactory.randomUuid()
    }

    fun makeUserModel() = UserModel().apply {
        id = DataFactory.randomUuid()
        name = DataFactory.randomUuid()
        email = DataFactory.randomUuid()
        pictureURL = DataFactory.randomUuid()
        description = DataFactory.randomUuid()
        grade = DataFactory.randomUuid()
        deviceToken = DataFactory.randomUuid()
        age = DataFactory.randomInt()
        level = DataFactory.randomInt()
        years = DataFactory.randomInt()
        teams = DataFactory.makeHashMap(30)
    }

    fun makePlayoneEntity() = PlayoneEntity.Entity(
        id = DataFactory.randomUuid(),
        name = DataFactory.randomUuid(),
        description = DataFactory.randomUuid(),
        date = DataFactory.randomLong(),
        updated = DataFactory.randomLong(),
        address = DataFactory.randomUuid(),
        longitude = DataFactory.randomDouble(),
        latitude = DataFactory.randomDouble(),
        limit = DataFactory.randomInt(),
        level = DataFactory.randomInt(),
        host = DataFactory.randomUuid(),
        userId = DataFactory.randomUuid(),
        coverUrl = DataFactory.randomUuid()
    )

    fun makeUserEntity() = UserEntity().apply {
        id = DataFactory.randomUuid()
        name = DataFactory.randomUuid()
        email = DataFactory.randomUuid()
        pictureURL = DataFactory.randomUuid()
        description = DataFactory.randomUuid()
        grade = DataFactory.randomUuid()
        deviceToken = DataFactory.randomUuid()
        age = DataFactory.randomInt()
        level = DataFactory.randomInt()
        years = DataFactory.randomInt()
        teams = DataFactory.makeHashMap(30)
    }
}