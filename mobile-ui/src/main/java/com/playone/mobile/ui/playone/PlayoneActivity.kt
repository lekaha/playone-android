package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.transition.Slide
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.OnBoardingViewModel
import com.playone.mobile.ui.onboarding.SignInFragment
import kotlinx.android.synthetic.main.activity_main.initializing
import javax.inject.Inject
import android.view.Gravity

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator

    @Inject lateinit var viewModelFactory: OnBoardingViewModel.OnBoardingViewModelFactory

    private lateinit var viewModel: OnBoardingViewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OnBoardingViewModel::class.java).apply {

                isProgressing.observe(this@PlayoneActivity, Observer {
                    it.ifTrue { showProgress() } otherwise { hideProgress() }
                })

                isSignedIn.observe(this@PlayoneActivity, Observer {
                    it.ifTrue {
                        Toast.makeText(
                            this@PlayoneActivity,
                            "Signed In", Toast.LENGTH_LONG
                        ).show()
                    } otherwise {
                        showSignInForms()
                    }
                })

                occurredError.observe(this@PlayoneActivity, Observer {
                    it?.apply(::showErrorState)
                })

                lifecycle::addObserver

                isSignedIn()
            }
    }

    private fun showSignInForms() {

        navigator.navigateTo(this) {

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