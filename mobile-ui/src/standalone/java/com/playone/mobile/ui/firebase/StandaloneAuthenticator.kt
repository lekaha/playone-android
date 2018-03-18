package com.playone.mobile.ui.firebase

import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.Credential

class StandaloneAuthenticator : Authenticator() {

    override fun signUp(email: String, password: String, callback: AuthResultCallBack) = TODO()

    override fun signIn(credential: Credential<*>, callback: AuthResultCallBack) = TODO()

    override fun signInAnonymously(callback: AuthResultCallBack) = TODO()

    override fun signOut(callback: AuthResultCallBack) = TODO()

    override fun isSignedIn() = false

    override fun sendEmailVerification(callback: AuthResultCallBack) = TODO()

    override fun getCurrentUser() = TODO()
}