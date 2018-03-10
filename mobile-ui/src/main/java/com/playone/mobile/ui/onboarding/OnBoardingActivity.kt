package com.playone.mobile.ui.onboarding

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.transition.Slide
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.OnBoardingViewModel
import kotlinx.android.synthetic.main.activity_main.initializing
import javax.inject.Inject
import android.view.Gravity
import com.playone.mobile.ui.navigateToActivity
import com.playone.mobile.ui.playone.PlayoneActivity

class OnBoardingActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator

    @Inject lateinit var viewModelFactory: OnBoardingViewModel.OnBoardingViewModelFactory

    private lateinit var viewModel: OnBoardingViewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OnBoardingViewModel::class.java).apply {

                isProgressing.observe(this@OnBoardingActivity, Observer {
                    it.ifTrue { showProgress() } otherwise { hideProgress() }
                })

                isSignedIn.observe(this@OnBoardingActivity, Observer {
                    it.ifTrue {
                        navigator.navigateToActivity<PlayoneActivity>(this@OnBoardingActivity) {
                            // TODO: Passing User to PlayoneActivity
                        }
                    } otherwise {
                        showSignInForms()
                    }
                })

                occurredError.observe(this@OnBoardingActivity, Observer {
                    it?.apply(::showErrorState)
                })

                lifecycle::addObserver

                isSignedIn()
            }
    }

    private fun showSignInForms() {

        navigator.navigateToFragment(this) {

            val fragment = SignInFragment.newInstance()
            val slideTransition = Slide(Gravity.START)
            slideTransition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
            fragment.exitTransition = slideTransition
            fragment.reenterTransition = slideTransition
            fragment.allowEnterTransitionOverlap = false
            fragment.allowReturnTransitionOverlap = false

            replace(R.id.fragment_content, fragment)
        }
    }

    private fun showProgress() {

        initializing.visibility = View.VISIBLE
    }

    private fun hideProgress() {

        initializing.visibility = View.GONE
    }

    private fun showErrorState(throwable: Throwable) {

        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(throwable.message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
            .show()
    }

    override fun onStop() {

        super.onStop()

        // TODO: Only for testing since now only have one page
        FirebaseAuth.getInstance().signOut()
    }
}