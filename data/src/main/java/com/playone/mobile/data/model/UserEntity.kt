package com.playone.mobile.data.model

/**
 * @author  Jieyi Wu
 * @since   2018/02/17
 */
data class UserEntity(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var pictureURL: String = "",
    var description: String = "",
    var grade: String = "",
    var age: Int = 0,
    var level: Int = 0,
    var years: Int = 0,
    var teams: HashMap<String, Any> = hashMapOf()
) : IPlayoneEntity