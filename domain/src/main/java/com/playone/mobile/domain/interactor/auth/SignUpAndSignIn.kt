package com.playone.mobile.domain.interactor.auth

import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.Credential
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.UseCase
import com.playone.mobile.domain.model.User
import com.playone.mobile.domain.repository.PlayoneRepository
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Use case used for sign in and sign up by given credentials
 */
class SignUpAndSignIn constructor(
    val playoneRepository: PlayoneRepository,
    private var authenticator: Authenticator,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun signInAnonymously(singleObserver: DisposableSingleObserver<User>) {

        authenticator.signInAnonymously(object : Authenticator.AuthResultCallBack {

            override fun onSuccessful(user: User) {

                // TODO: should get the user from repository after implementing Cache
//                val single = playoneRepository.getUserByEmail(user.email)
                execute(Single.just(user), singleObserver)
            }

            override fun onFailed(throwable: Throwable) {

                singleObserver.onError(throwable)
            }
        })
    }

    fun signIn(credential: Credential<*>, singleObserver: DisposableSingleObserver<User>) {

        authenticator.signIn(credential, object : Authenticator.AuthResultCallBack {

            override fun onSuccessful(user: User) {

                // TODO: should get the user from repository after implementing Cache
//                val single = playoneRepository.getUserByEmail(user.email)
                execute(Single.just(user), singleObserver)
            }

            override fun onFailed(throwable: Throwable) {

                singleObserver.onError(throwable)
            }
        })

    }

    fun signUp(email: String, password: String, singleObserver: DisposableSingleObserver<User>) {

        authenticator.signUp(email, password, object : Authenticator.AuthResultCallBack {
            override fun onSuccessful(user: User) {

                // TODO: should get the user from repository after implementing Cache
//                val single = playoneRepository.createUser(user).toSingle { user }
                execute(Single.just(user), singleObserver)
            }

            override fun onFailed(throwable: Throwable) {

                singleObserver.onError(throwable)
            }

        })
    }

    fun isSignedIn() = authenticator.isSignedIn()

    fun isVerifiedEmail() = authenticator.isVerifiedEmail()

    private fun execute(single: Single<User>, singleObserver: DisposableSingleObserver<User>) {

        single.subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
        addDisposable(single.subscribeWith(singleObserver))
    }

}