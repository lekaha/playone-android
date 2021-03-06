package com.playone.mobile.presentation.onBoarding

import com.playone.mobile.domain.Credential
import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.UserView
import io.reactivex.observers.DisposableSingleObserver

class LoginPlayonePresenter(
    val signUpAndSignIn: SignUpAndSignIn,
    val getCurrentUser: GetCurrentUser,
    val viewMapper: Mapper<UserView, User>
) : LoginPlayoneContract.Presenter {

    var loginView: LoginPlayoneContract.View? = null

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        loginView = null
    }

    override fun setView(
        view: LoginPlayoneContract.View
    ) {
        loginView = view
    }

    override fun signUp(email: String, password: String) {

        loginView?.onResponse(ViewResponse.loading())
        signUpAndSignIn.signUp(email, password, SignInUpSubscriber())
    }

    override fun signInAnonymously() {

        loginView?.onResponse(ViewResponse.loading())
        signUpAndSignIn.signInAnonymously(SignInUpSubscriber())
    }

    override fun signIn(email: String, password: String) {

        loginView?.onResponse(ViewResponse.loading())
        signUpAndSignIn.signIn(EmailPasswordCredential(email, password), SignInUpSubscriber())
    }

    override fun signIn(secretContent: Any) {

        signUpAndSignIn.signIn(SecretCredential(secretContent), SignInUpSubscriber())
    }

    override fun isSignedIn() = signUpAndSignIn.isSignedIn()

    override fun sendEmailVerificationToCurrentUser() {

        loginView?.onResponse(ViewResponse.loading())
        signUpAndSignIn.sendEmailVerification(SignInUpSubscriber())
    }

    override fun getCurrentUser() {
        getCurrentUser.execute(GetCurrentUserSubscriber())
    }

    inner class EmailPasswordCredential(
        private val email: String,
        private val password: String
    ) : Credential<Pair<String, String>>() {

        override fun isSocialNetworkCredential() = false

        override fun getContent() = Pair(email, password)
    }

    inner class SecretCredential(
        private val secret: Any
    ) : Credential<Any>() {

        override fun isSocialNetworkCredential() = true

        override fun getContent() = secret
    }

    inner class SignInUpSubscriber : DisposableSingleObserver<User>() {

        override fun onSuccess(t: User) {

            loginView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {

            loginView?.onResponse(ViewResponse.error(e))
        }
    }

    inner class GetCurrentUserSubscriber: DisposableSingleObserver<User>() {

        override fun onSuccess(t: User) {
            loginView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {
            loginView?.onResponse(ViewResponse.error(e))
        }

    }
}