package com.playone.mobile.domain

import com.playone.mobile.domain.model.User
import io.reactivex.Single

abstract class Authenticator {

    abstract fun signUp(email: String, password: String): Single<User>

    abstract fun signIn(credential: Credential): Single<User>

    abstract fun signOut(): Single<Unit>

    abstract fun isSignIn(): Boolean
}