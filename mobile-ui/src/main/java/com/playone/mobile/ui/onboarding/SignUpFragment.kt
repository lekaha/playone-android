package com.playone.mobile.ui.onboarding

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.LoginViewModel
import kotlinx.android.synthetic.main.merge_signup.instruction
import kotlinx.android.synthetic.main.merge_signup.login_name_field
import kotlinx.android.synthetic.main.merge_signup.login_password_field
import kotlinx.android.synthetic.main.merge_signup.login_repassword_field
import kotlinx.android.synthetic.main.merge_signup.sign_up_action_btn
import kotlinx.android.synthetic.main.merge_signup.sign_up_skip_btn
import javax.inject.Inject

class SignUpFragment : BaseInjectingFragment() {

    @Inject lateinit var navigator: Navigator

    private lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun getLayoutId() = R.layout.fragment_signup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(LoginViewModel::class.java)
            showSignUpForms()
        }
    }

    private fun showSignUpForms() {

        view?.apply {
            // Sign up with email and password
            sign_up_action_btn.setOnClickListener {

                // Password and Re-password should be the same
                (login_password_field.text.toString() == login_repassword_field.text.toString())
                    .ifTrue {
                        viewModel.signUp(
                            login_name_field.text.toString(),
                            login_password_field.text.toString())
                    } otherwise {
                    // TODO: Should use another presentation
                    Toast.makeText(
                        activity,
                        "Password is not matched", Toast.LENGTH_LONG
                    ).show()
                }
            }

            sign_up_skip_btn.setOnClickListener {
                viewModel.signInAnonymously()
            }

            val resendString = "Re-send"
            val instructionString = getString(R.string.signup_instruction)
            val idxResend = instructionString.lastIndexOf(resendString)
            val spannableString = SpannableString(instructionString)

            spannableString.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View?) {
                    viewModel.sendEmailVerification()
                }},
                idxResend,
                idxResend + resendString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            instruction.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }
}