package com.playone.mobile.ui.onboarding

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_signup.progress
import kotlinx.android.synthetic.main.merge_signup.login_name_field
import kotlinx.android.synthetic.main.merge_signup.login_password_field
import kotlinx.android.synthetic.main.merge_signup.sign_up_action_btn
import kotlinx.android.synthetic.main.merge_signup.sign_up_skip_btn
import javax.inject.Inject

class SignUpFragment : BaseInjectingFragment() {

    @Inject lateinit var viewModelFactory: SignUpViewModel.SignUpViewModelFactory

    @Inject lateinit var navigator: Navigator

    private lateinit var viewModel: SignUpViewModel

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun getLayoutId() = R.layout.fragment_signup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        hideProgress()
    }

    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SignUpViewModel::class.java).apply {
                isProgressing.observe(this@SignUpFragment, Observer {
                    it.ifTrue { showProgress() } otherwise { hideProgress() }
                })

                isSignedIn.observe(this@SignUpFragment, Observer {
                    it.ifTrue {
                        Toast.makeText(
                            activity,
                            "Signed In",
                            Toast.LENGTH_LONG
                        ).show()
                    } otherwise {
                        showSignUpForms()
                    }
                })

                occurredError.observe(this@SignUpFragment, Observer {
                    it?.apply(::showErrorState)
                })

                lifecycle::addObserver
            }
    }

    private fun showSignUpForms() {

        view?.apply {
            // Sign up with email and password
            sign_up_action_btn.setOnClickListener {
                viewModel.signUp(
                    login_name_field.text.toString(),
                    login_password_field.text.toString())
            }

            sign_up_skip_btn.setOnClickListener {
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

    private fun showErrorState(throwable: Throwable) {

        activity ?: AlertDialog.Builder(activity!!)
            .setTitle("Error")
            .setMessage(throwable.message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
            .show()
    }
}