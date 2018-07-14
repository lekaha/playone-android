package com.playone.mobile.remote.model

import kotlin.properties.Delegates

/**
 * Representation for a [UserModel] fetched from the API.
 */
class UserModel {

    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var pictureURL: String
    lateinit var description: String
    lateinit var grade: String
    lateinit var deviceToken: String
    var age by Delegates.notNull<Int>()
    var level by Delegates.notNull<Int>()
    var years by Delegates.notNull<Int>()
    lateinit var teams: HashMap<String, Any>
    lateinit var favorites: HashMap<String, Boolean>
}