package com.playone.mobile.ui.onboarding

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.initializing
import kotlinx.android.synthetic.main.merge_login.view.facebook_login_btn
import kotlinx.android.synthetic.main.merge_login.view.google_login_btn
import kotlinx.android.synthetic.main.merge_login.view.login_action_button
import kotlinx.android.synthetic.main.merge_login.view.login_name_field
import kotlinx.android.synthetic.main.merge_login.view.login_password_field
import javax.inject.Inject

class SignInFragment : BaseInjectingFragment() {

    companion object {
        const val GOOGLE_SIGN_IN = 0x101

        fun newInstance() = SignInFragment()
    }

    @Inject lateinit var viewModelFactory: LoginViewModel.LoginViewModelFactory

    @Inject lateinit var googleSignInClient: GoogleSignInClient

    @Inject lateinit var callbackManager: CallbackManager

    @Inject lateinit var loginManager: LoginManager

    @Inject lateinit var readPermissions: ArrayList<String>

    private lateinit var viewModel: LoginViewModel

    override fun getLayoutId() = R.layout.fragment_signin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java).apply {

                isProgressing.observe(this@SignInFragment, Observer {
                    it.ifTrue { showProgress() } otherwise { hideProgress() }
                })

                isSignedIn.observe(this@SignInFragment, Observer {
                    it.ifTrue {
                        Toast.makeText(
                            activity,
                            "Signed In", Toast.LENGTH_LONG
                        ).show()
                    } otherwise {
                        showSignInForms()
                    }
                })

                occurredError.observe(this@SignInFragment, Observer {
                    it?.apply(::showErrorState)
                })

                lifecycle::addObserver

                isSignedIn()
            }
    }

    private fun showSignInForms() {

        view?.apply {

            val signInView =
                layoutInflater.inflate(R.layout.merge_login, view!! as ViewGroup, false)

            // Sign in with email and password
            signInView.login_action_button.setOnClickListener {
                viewModel.signIn(
                    signInView.login_name_field.text.toString(),
                    signInView.login_password_field.text.toString())
            }

            // Sign in with Google
            signInView.google_login_btn.setOnClickListener {
                googleSignIn()
            }

            // Sign in with Facebook
            signInView.facebook_login_btn.setOnClickListener {
                facebookSignIn()
            }
        }

    }

    private fun googleSignIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun facebookSignIn() {

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

        activity ?: AlertDialog.Builder(activity!!)
            .setTitle("Error")
            .setMessage(throwable.message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
            .show()
    }
}