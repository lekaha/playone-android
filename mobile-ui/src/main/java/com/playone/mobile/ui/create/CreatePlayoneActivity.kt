package com.playone.mobile.ui.create

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.View
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.R
import com.playone.mobile.ui.view.TransitionHelper
import kotlinx.android.synthetic.main.activity_create.create_layout

class CreatePlayoneActivity : BaseActivity(), TransitionHelper.Listener {

    override fun onBeforeEnter(contentView: View) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var transitionHelper: TransitionHelper

    override fun onBeforeViewShows(contentView: View) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAfterEnter() {

        animateRevealShow(create_layout, cx, cy, 600)
    }

    override fun onBeforeBack(): Boolean {

        animateRevealHide(create_layout, cx, cy, 600)
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
        transitionHelper.onSaveInstanceState(outState)
    }

    override fun onResume() {

        super.onResume()
        transitionHelper.onResume()
    }

    override fun onBackPressed() {

        transitionHelper.onBackPressed()
        super.onBackPressed()
    }

    override fun onBeforeReturn() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId() = R.layout.activity_create

    var cx: Int = -1
    var cy: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionHelper.excludeEnterTarget(this, R.id.action_bar, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.statusBarBackground, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.navigationBarBackground, true)
        }

        cx = intent.getIntExtra("CX", -1)
        cy = intent.getIntExtra("CY", -1)

        create_layout.visibility = View.INVISIBLE
        transitionHelper = TransitionHelper(this, savedInstanceState).also {
            it.addListener(this)
            it.onViewCreated()
        }
    }
}