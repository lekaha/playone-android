package com.playone.mobile.data.test.factory

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User

object Factory {

    fun makePlayone() = Playone().apply {
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

    fun makeUser() = User().apply {
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

    fun makePlayoneEntity() = PlayoneEntity().apply {
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

    fun makePlayoneList(count: Int) = mutableListOf<PlayoneEntity>().apply {
        repeat(count) { add(makePlayoneEntity()) }
    }
}