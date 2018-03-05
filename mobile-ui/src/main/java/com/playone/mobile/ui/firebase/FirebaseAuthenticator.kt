package com.playone.mobile.ui.firebase

import android.os.Handler
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.Credential
import com.playone.mobile.domain.model.User
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.ui.ext.ifFalse
import com.playone.mobile.ui.ext.ifTrue

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
                callback.onFailed()
            }
        }
    }

    override fun signIn(credential: Credential, callback: AuthResultCallBack) {

        credential.isSocialNetworkCredential().ifFalse {
            val content = credential.getContent<Pair<String, String>>()
            firebaseAuth.signInWithEmailAndPassword(content.first, content.second)
                .addOnCompleteListener {

                    it.isSuccessful.ifTrue {
                        firebaseAuth.currentUser?.let {
                            callback.onSuccessful(mapper.mapToUser(it))
                        }
                    }

                    it.isSuccessful.ifFalse {
                        callback.onFailed()
                    }
                }
        }

        credential.isSocialNetworkCredential().ifTrue {
            val content = credential.getContent<AuthCredential>()
            firebaseAuth.signInWithCredential(content)
                .addOnCompleteListener {

                    it.isSuccessful.ifTrue {
                        firebaseAuth.currentUser?.let {
                            callback.onSuccessful(mapper.mapToUser(it))
                        }
                    }

                    it.isSuccessful.ifFalse {
                        callback.onFailed()
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

                callback.onFailed()
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
                pictureURL = user.photoUrl?.toString() ?: ""
            )

    }
}