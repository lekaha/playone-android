package com.playone.mobile.ui.playone

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.os.postDelayed
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

        // TODO: Should remove this. Just for demo now
        Handler().postDelayed(3000L) {
            showSignInForms()
        }
    }

    private fun showSignInForms() {
        initializing.visibility = View.GONE
        layoutInflater.inflate(R.layout.merge_login, content_layout, true)
    }
}