package com.playone.mobile.ui.firebase

import android.os.Handler
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.Credential
import com.playone.mobile.domain.model.User
import com.playone.mobile.ext.ifFalse
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise

class FirebaseAuthenticator(
    private val firebaseAuth: FirebaseAuth
) : Authenticator() {

    val mapper = FirebaseUserMapper()

    override fun signUp(email: String, password: String, callback: AuthResultCallBack) {

        // Difference between Activity-scoped and no-Activity scoped listener:
        // https://developers.google.com/android/reference/com/google/android/gms/tasks/Task#public-methods
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            it.isSuccessful.ifTrue {
                firebaseAuth.currentUser?.let {
                    callback.onSuccessful(mapper.mapToUser(it))
                }
            } otherwise {
                callback.onFailed(it.exception ?: Exception("Unknown failed"))
            }
        }
    }

    override fun signInAnonymously(callback: AuthResultCallBack) {

        firebaseAuth.signInAnonymously().addOnCompleteListener {
            it.isSuccessful.ifTrue {
                firebaseAuth.currentUser?.let {
                    callback.onSuccessful(mapper.mapToUser(it))
                }
            } otherwise {
                callback.onFailed(it.exception ?: Exception("Unknown failed"))
            }
        }
    }

    override fun signIn(credential: Credential<*>, callback: AuthResultCallBack) {

        credential.isSocialNetworkCredential().ifFalse {
            val content = credential.getContent() as Pair<*, *>
            firebaseAuth.signInWithEmailAndPassword(content.first as String,
                                                    content.second as String)
                .addOnCompleteListener {

                    it.isSuccessful.ifTrue {
                        firebaseAuth.currentUser?.let {
                            callback.onSuccessful(mapper.mapToUser(it))
                        }
                    } otherwise {
                        callback.onFailed(it.exception ?: Exception("Unknown failed"))
                    }
                }
        }

        credential.isSocialNetworkCredential().ifTrue {
            val content = credential.getContent()
            val socialCredential = when (content) {

                is GoogleSignInAccount ->
                    GoogleAuthProvider.getCredential((content).idToken, null)
                is AccessToken -> FacebookAuthProvider.getCredential((content).token)
                else -> throw IllegalStateException("Unidentified Credential")
            }

            socialCredential.apply {
                firebaseAuth.signInWithCredential(this)
                    .addOnCompleteListener {

                        it.isSuccessful.ifTrue {

                            firebaseAuth.currentUser?.let {
                                callback.onSuccessful(mapper.mapToUser(it))
                            }
                        } otherwise {

                            callback.onFailed(it.exception ?: Exception("Unknown failed"))
                        }
                    }
            }
        }
    }

    override fun signOut(callback: AuthResultCallBack) {

        firebaseAuth.currentUser?.let {
            firebaseAuth.signOut().apply {

                Handler().post {

                    callback.onSuccessful(mapper.mapToUser(it))
                }
            }
        } ?: run {

            Handler().post {

                callback.onFailed(Exception("Already singed out"))
            }
        }
    }

    override fun isSignedIn() = firebaseAuth.currentUser != null

    override fun sendEmailVerification(callback: AuthResultCallBack) {

        firebaseAuth.currentUser?.apply {
            this.sendEmailVerification().addOnCompleteListener {
                it.isSuccessful.ifTrue {

                    firebaseAuth.currentUser?.let {
                        callback.onSuccessful(mapper.mapToUser(it))
                    }
                } otherwise {

                    callback.onFailed(it.exception ?: Exception("Unknown failed"))
                }
             }
        }



    }

    class FirebaseUserMapper {

        fun mapToUser(user: FirebaseUser) =
            User().apply {
                id = user.uid
                email = user.email.orEmpty()
                name = user.displayName.orEmpty()
                pictureURL = user.photoUrl?.toString().orEmpty()
                isVerified = user.isEmailVerified
            }

    }
}