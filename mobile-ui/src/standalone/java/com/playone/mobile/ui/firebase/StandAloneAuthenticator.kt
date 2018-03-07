package com.playone.mobile.ui.firebase

import com.google.firebase.auth.FirebaseUser
import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.Credential
import com.playone.mobile.domain.model.User

class StandAloneAuthenticator : Authenticator() {

    val mapper = FirebaseUserMapper()

    override fun signUp(email: String, password: String, callback: AuthResultCallBack) = TODO()

    override fun signIn(credential: Credential<*>, callback: AuthResultCallBack) = TODO()

    override fun signOut(callback: AuthResultCallBack) = TODO()

    override fun isSignedIn() = false

    class FirebaseUserMapper {

        fun mapToUser(user: FirebaseUser) =
            User().apply {
                id = user.email.orEmpty()
                email = user.email.orEmpty()
                name = user.displayName.orEmpty()
                pictureURL = user.photoUrl?.toString().orEmpty()
            }

    }
}