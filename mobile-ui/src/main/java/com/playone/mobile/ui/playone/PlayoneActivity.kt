package com.playone.mobile.ui.playone

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.R
import kotlinx.android.synthetic.main.activity_playone.bottom_navigation

class PlayoneActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_playone

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_sign_out -> {
                    FirebaseAuth.getInstance().signOut()
                    finish()
                    true

                }
                else -> false
            }
        }
    }
}