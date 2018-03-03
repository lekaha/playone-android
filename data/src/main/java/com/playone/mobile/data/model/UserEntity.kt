package com.playone.mobile.data.model

/**
 * Representation for a [UserEntity] fetched from an external layer data source.
 */
data class UserEntity(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var pictureURL: String = "",
    var description: String = "",
    var grade: String = "",
    var deviceToken: String = "",
    var age: Int = 0,
    var level: Int = 0,
    var years: Int = 0,
    var teams: HashMap<String, Any> = hashMapOf()
) : PlayoneItem