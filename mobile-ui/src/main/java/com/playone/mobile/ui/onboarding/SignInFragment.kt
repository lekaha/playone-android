package com.playone.mobile.ui.onboarding

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.transition.Slide
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
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
import com.playone.mobile.ext.ifFalse
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.LoginViewModel
import kotlinx.android.synthetic.main.fragment_signin.progress
import kotlinx.android.synthetic.main.merge_login.login_skip_btn
import kotlinx.android.synthetic.main.merge_login.view.facebook_login_btn
import kotlinx.android.synthetic.main.merge_login.view.google_login_btn
import kotlinx.android.synthetic.main.merge_login.view.login_action_button
import kotlinx.android.synthetic.main.merge_login.view.login_name_field
import kotlinx.android.synthetic.main.merge_login.view.login_password_field
import kotlinx.android.synthetic.main.merge_login.view.sign_up_btn
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

    @Inject lateinit var navigator: Navigator

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
                            "Signed In",
                            Toast.LENGTH_LONG
                        ).show()
                    } otherwise {
                        showSignInForms()
                    }
                })

                isVerifiedEmail.observe(this@SignInFragment, Observer {
                    it.ifFalse {
                        Toast.makeText(
                            activity,
                            "Not yet verified Email", Toast.LENGTH_LONG
                        ).show()
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
            // Sign in with email and password
            login_action_button.setOnClickListener {
                viewModel.signIn(
                    login_name_field.text.toString(),
                    login_password_field.text.toString())
            }

            // Sign in with Google
            google_login_btn.setOnClickListener {
                googleSignIn()
            }

            // Sign in with Facebook
            facebook_login_btn.setOnClickListener {
                facebookSignIn()
            }

            sign_up_btn.setOnClickListener {
                navigator.navigateTo((activity as AppCompatActivity)) {

                    val fragment = SignUpFragment.newInstance()
                    val slideTransition = Slide(Gravity.RIGHT)
                    slideTransition.duration =
                        resources.getInteger(R.integer.anim_duration_long).toLong()
                    fragment.exitTransition = slideTransition
                    fragment.enterTransition = slideTransition
                    fragment.allowEnterTransitionOverlap = false
                    fragment.allowReturnTransitionOverlap = false

                    replace(R.id.fragment_content, fragment)
                    addToBackStack(null)
                }
            }

            login_skip_btn.setOnClickListener {
                viewModel.signInAnonymously()
            }
        }
    }

    private fun showProgress() {

        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {

        progress.visibility = View.GONE
    }

    private fun googleSignIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun facebookSignIn() {
        val accessToken = AccessToken.getCurrentAccessToken()
        (accessToken != null).ifTrue {

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

    private fun showErrorState(throwable: Throwable) {

        activity?.apply {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(throwable.message)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
                .show()
        }
    }
}