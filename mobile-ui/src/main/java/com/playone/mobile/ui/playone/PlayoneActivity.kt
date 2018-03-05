package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.content_layout
import kotlinx.android.synthetic.main.activity_main.initializing
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: LoginViewModel.LoginViewModelFactory

    private lateinit var viewModel: LoginViewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        viewModel.isProgressing.observe(this, Observer {
            it?.takeIf { it }?.apply { showProgress() } ?: hideProgress()
        })
        viewModel.isSignedIn.observe(this, Observer {
            it?.takeIf { it }?.apply {
                // TODO: proceed to the main page with logged in state
            } ?: showSignInForms()
        })
        viewModel.occurredError.observe(this, Observer {
            it?.takeIf { it.isNotNull() }?.apply { showErrorState(it) }
        })
        viewModel.let { lifecycle.addObserver(it) }

        viewModel.isSignedIn()
    }

    private fun showSignInForms() {
        val view = layoutInflater.inflate(R.layout.merge_login, content_layout, true)

        // Sign in with email and password
        val emailEditText = view.findViewById<EditText>(R.id.login_name_field)
        val passwordEditText = view.findViewById<EditText>(R.id.login_password_field)
        view.findViewById<Button>(R.id.login_action_button).setOnClickListener {
            viewModel.signIn(emailEditText.text.toString(), passwordEditText.text.toString())
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