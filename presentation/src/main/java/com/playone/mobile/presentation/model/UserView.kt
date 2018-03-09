package com.playone.mobile.presentation.model

import kotlin.properties.Delegates

class UserView {

    lateinit var name: String
    lateinit var email: String
    var isVerified by Delegates.notNull<Boolean>()
}