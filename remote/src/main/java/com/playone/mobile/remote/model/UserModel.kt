package com.playone.mobile.remote.model

/**
 * Representation for a [UserModel] fetched from the API.
 */
data class UserModel(
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
)