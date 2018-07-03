package com.playone.mobile.presentation.model

import java.io.Serializable
import kotlin.properties.Delegates

class UserView: Serializable {

    lateinit var name: String
    lateinit var email: String
    lateinit var pictureURL: String
    var isVerified by Delegates.notNull<Boolean>()
}