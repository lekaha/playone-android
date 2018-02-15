package com.playone.mobile.ui.browse

import android.os.Bundle
import android.support.v4.app.Fragment
import com.playone.mobile.ui.BaseInjectingActivity
import com.playone.mobile.ui.R

class BrowseActivity:
        BaseInjectingActivity<Any>() {

    override fun createComponent(): Any? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.activity_browse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        savedInstanceState?.let {

        } ?: run {
            replaceFragment(BrowseFragment.newInstance())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}