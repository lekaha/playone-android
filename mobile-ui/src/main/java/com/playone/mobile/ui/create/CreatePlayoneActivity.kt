package com.playone.mobile.ui.create

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.view.TransitionHelper
import kotlinx.android.synthetic.main.activity_create.create_layout
import javax.inject.Inject

class CreatePlayoneActivity : BaseActivity(), TransitionHelper.Listener {

    private var cx: Int = CIRCULAR_REVEAL_DEFAULT_X
    private var cy: Int = CIRCULAR_REVEAL_DEFAULT_Y

    private lateinit var transitionHelper: TransitionHelper

    @Inject
    lateinit var navigator: Navigator

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

    override fun onBeforeEnter(contentView: View) {
    }

    override fun onBeforeViewShows(contentView: View) {
    }

    override fun onBeforeReturn() {
    }

    override fun getLayoutId() = R.layout.activity_create

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        navigator.navigateToFragment(this) {
            replace(R.id.create_layout, SelectLocationFragment.newInstance())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionHelper.excludeEnterTarget(this, R.id.action_bar, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.statusBarBackground, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.navigationBarBackground, true)
        }

        cx = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, CIRCULAR_REVEAL_DEFAULT_X)
        cy = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, CIRCULAR_REVEAL_DEFAULT_Y)

        create_layout.visibility = View.INVISIBLE
        transitionHelper = TransitionHelper(this, savedInstanceState).also {
            it.addListener(this)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {

        super.onPostCreate(savedInstanceState, persistentState)
        transitionHelper.onViewCreated()
    }

    companion object {
        const val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"

        // Default circular reveal from left top
        const val CIRCULAR_REVEAL_DEFAULT_X = 50
        const val CIRCULAR_REVEAL_DEFAULT_Y = 50
    }
}