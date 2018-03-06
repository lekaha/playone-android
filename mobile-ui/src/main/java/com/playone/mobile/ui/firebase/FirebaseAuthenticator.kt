package com.playone.mobile.ui.firebase

import android.os.Handler
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
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
            }

            it.isSuccessful.ifFalse {
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

            var socialCredential: AuthCredential? = null
            (content is GoogleSignInAccount).ifTrue {
                socialCredential =
                    GoogleAuthProvider.getCredential((content as GoogleSignInAccount).idToken, null)
            }

            (content is AccessToken).ifTrue {
                socialCredential =
                    FacebookAuthProvider.getCredential((content as AccessToken).token)
            }

            socialCredential?.apply {
                firebaseAuth.signInWithCredential(socialCredential!!)
                    .addOnCompleteListener {

                        it.isSuccessful.ifTrue {
                            firebaseAuth.currentUser?.let {
                                callback.onSuccessful(mapper.mapToUser(it))
                            }
                        } otherwise {
                            callback.onFailed(it.exception ?: Exception("Unknown failed"))
                        }
                    }
            } ?: run {
                throw IllegalStateException("Unidentified Credential")
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

    class FirebaseUserMapper {

        fun mapToUser(user: FirebaseUser) =
            User(
                id = user.email.orEmpty(),
                email = user.email.orEmpty(),
                name = user.displayName.orEmpty(),
                pictureURL = user.photoUrl?.toString().orEmpty()
            )

    }
}