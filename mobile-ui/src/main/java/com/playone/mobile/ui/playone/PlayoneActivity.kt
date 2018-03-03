package com.playone.mobile.ui.playone

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.os.postDelayed
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.R
import kotlinx.android.synthetic.main.activity_main.content_layout
import kotlinx.android.synthetic.main.activity_main.initializing

class PlayoneActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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