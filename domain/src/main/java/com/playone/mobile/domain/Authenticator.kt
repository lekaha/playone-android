package com.playone.mobile.domain

import com.playone.mobile.domain.model.User

abstract class Authenticator {

    abstract fun signUp(email: String, password: String, callback: AuthResultCallBack)

    abstract fun signIn(credential: Credential<*>, callback: AuthResultCallBack)

    abstract fun signInAnonymously(callback: AuthResultCallBack)

    abstract fun signOut(callback: AuthResultCallBack)

    abstract fun isSignedIn(): Boolean

    abstract fun isVerifiedEmail(): Boolean

    abstract fun sendEmailVerification(callback: AuthResultCallBack)

    interface AuthResultCallBack {

        fun onSuccessful(user: User)
        fun onFailed(throwable: Throwable)
    }
}