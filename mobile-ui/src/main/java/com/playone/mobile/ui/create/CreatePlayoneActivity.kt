package com.playone.mobile.ui.create

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.ext.v
import com.playone.mobile.ui.model.CreatePlayoneViewModel
import com.playone.mobile.ui.view.TransitionHelper
import kotlinx.android.synthetic.main.activity_create.create_layout
import kotlinx.android.synthetic.main.activity_create.progress
import javax.inject.Inject

class CreatePlayoneActivity : BaseActivity(), TransitionHelper.Listener {

    private var cx: Int = CIRCULAR_REVEAL_DEFAULT_X
    private var cy: Int = CIRCULAR_REVEAL_DEFAULT_Y

    private lateinit var transitionHelper: TransitionHelper

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var createPlayoneViewModelFactory: CreatePlayoneViewModel.CreatePlayoneViewModelFactory

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

        if (supportFragmentManager.backStackEntryCount == 0) {
            transitionHelper.onBackPressed()
            super.onBackPressed()
            //additional code
        }
        else {
            supportFragmentManager.popBackStack()
        }

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

        isGooglePlayServicesAvailable()

        ViewModelProviders.of(this, createPlayoneViewModelFactory)
            .get(CreatePlayoneViewModel::class.java).apply {
                isPlayoneCreated.observe(this@CreatePlayoneActivity, Observer {
                    it?.ifTrue {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                })

                isProgressing.observe(this@CreatePlayoneActivity, Observer {
                    it?.let { isProgressing ->
                        progress.visibility = if (isProgressing) View.VISIBLE else View.GONE
                    } ?: run {
                        progress.visibility = View.GONE
                    }
                    progress.isClickable = false
                    progress.isEnabled = false
                })
            }


        navigator.navigateToFragment(this) {
            val fragment = SelectLocationFragment.newInstance()
            fragment.allowEnterTransitionOverlap = true
            fragment.allowReturnTransitionOverlap = true
            replace(R.id.fragment_content, fragment)
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

    private fun isGooglePlayServicesAvailable(): Boolean {
        // Check that Google Play services is available
        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            v("Location Updates", "Google Play services is available.")
            return true
        }
        else {
            // Get the error dialog from Google Play services

            // If Google Play services can provide an error dialog
            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                                                  CONNECTION_FAILURE_RESOLUTION_REQUEST)
                ?.show()

            return false
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

        const val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    }
}