package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.R
import com.playone.mobile.ui.ext.ifTrue
import com.playone.mobile.ui.ext.otherwise
import com.playone.mobile.ui.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.content_layout
import kotlinx.android.synthetic.main.activity_main.initializing
import kotlinx.android.synthetic.main.merge_login.view.login_action_button
import kotlinx.android.synthetic.main.merge_login.view.login_name_field
import kotlinx.android.synthetic.main.merge_login.view.login_password_field
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: LoginViewModel.LoginViewModelFactory

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
                        // TODO: proceed to the main page with logged in state
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

    private fun showSignInForms() {

        val view = layoutInflater.inflate(R.layout.merge_login, content_layout, true)

        // Sign in with email and password
        view.login_action_button.setOnClickListener {
            viewModel.signIn(
                view.login_name_field.text.toString(),
                view.login_password_field.text.toString())
        }

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