package com.playone.mobile.ui.browse

import android.os.Bundle
import com.playone.mobile.ui.BaseInjectingActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import javax.inject.Inject

class BrowseActivity:
        BaseInjectingActivity<Any>() {

    @Inject lateinit var navigator: Navigator

    override fun createComponent(): Any? {
        return null
    }

    override fun getLayoutId() = R.layout.activity_browse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        savedInstanceState?.let {

        } ?: run {
            navigator.navigateTo(this, {
                replace(R.id.content, BrowseFragment.newInstance())
            })
        }
    }
}