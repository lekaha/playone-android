package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.content_layout
import kotlinx.android.synthetic.main.activity_main.initializing
import kotlinx.android.synthetic.main.merge_login.view.facebook_login_btn
import kotlinx.android.synthetic.main.merge_login.view.google_login_btn
import kotlinx.android.synthetic.main.merge_login.view.login_action_button
import kotlinx.android.synthetic.main.merge_login.view.login_name_field
import kotlinx.android.synthetic.main.merge_login.view.login_password_field
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    companion object {
        const val GOOGLE_SIGN_IN = 0x101
    }

    @Inject lateinit var viewModelFactory: LoginViewModel.LoginViewModelFactory

    @Inject lateinit var googleSignInClient: GoogleSignInClient

    @Inject lateinit var callbackManager: CallbackManager

    @Inject lateinit var loginManager: LoginManager

    @Inject lateinit var readPermissions: ArrayList<String>

    private lateinit var viewModel: LoginViewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java).apply {

                isProgressing.observe(this@PlayoneActivity, Observer {
                    it.ifTrue { showProgress() } otherwise { hideProgress() }
                })

                isSignedIn.observe(this@PlayoneActivity, Observer {
                    it.ifTrue {
                        Toast.makeText(
                            this@PlayoneActivity,
                            "Signed In", Toast.LENGTH_LONG
                        ).show()
                    } otherwise {
                        showSignInForms()
                    }
                })

                occurredError.observe(this@PlayoneActivity, Observer {
                    it?.apply(::showErrorState)
                })

                lifecycle::addObserver

                isSignedIn()
            }
    }

    override fun onStop() {

        super.onStop()

        // TODO: Only for testing since now only have one page
        FirebaseAuth.getInstance().signOut()
    }

    private fun showSignInForms() {

        val view = layoutInflater.inflate(R.layout.merge_login, content_layout, true)

        // Sign in with email and password
        view.login_action_button.setOnClickListener {
            viewModel.signIn(
                view.login_name_field.text.toString(),
                view.login_password_field.text.toString())
        }

        // Sign in with Google
        view.google_login_btn.setOnClickListener {
            googleSignIn()
        }

        // Sign in with Facebook
        view.facebook_login_btn.setOnClickListener {
            facebookSignIn()
        }

    }

    private fun googleSignIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun facebookSignIn() {

        val accessToken = AccessToken.getCurrentAccessToken()
        (accessToken!= null).ifTrue {

            viewModel.signIn(accessToken)
        } otherwise {

            loginManager.logInWithReadPermissions(this, readPermissions)
            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {

                    result?.apply {
                        viewModel.signIn(result.accessToken)
                    } ?: showErrorState(Exception("Missing token"))

                }

                override fun onCancel() {

                    showErrorState(Exception("Facebook Sign in canceled"))
                }

                override fun onError(error: FacebookException?) {

                    showErrorState(error ?: Exception("Facebook signed in error"))
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        (requestCode == GOOGLE_SIGN_IN).ifTrue {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                viewModel.signIn(account)
            }
            catch (e: ApiException) {
                showErrorState(Exception("Could not signed in successfully"))
            }
        }

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun showProgress() {

        initializing.visibility = View.VISIBLE
    }

    private fun hideProgress() {

        initializing.visibility = View.GONE
    }

    private fun showErrorState(throwable: Throwable) {

        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(throwable.message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
            .show()
    }
}