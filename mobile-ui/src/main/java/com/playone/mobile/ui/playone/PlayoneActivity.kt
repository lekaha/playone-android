package com.playone.mobile.ui.playone

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.onboarding.SignInFragment
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        navigator.navigateTo(this) {
            replace(R.id.fragment_content, SignInFragment.newInstance())
        }
    }

    override fun onStop() {

        super.onStop()

        // TODO: Only for testing since now only have one page
        FirebaseAuth.getInstance().signOut()
    }
}