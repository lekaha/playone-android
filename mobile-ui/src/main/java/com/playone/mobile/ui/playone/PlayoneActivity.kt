package com.playone.mobile.ui.playone

import android.animation.ObjectAnimator
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.IntDef
import android.support.design.bottomappbar.BottomAppBar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.widget.toast
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.create.CreatePlayoneActivity
import com.playone.mobile.ui.create.CreatePlayoneActivity.Companion.EXTRA_CIRCULAR_REVEAL_X
import com.playone.mobile.ui.create.CreatePlayoneActivity.Companion.EXTRA_CIRCULAR_REVEAL_Y
import com.playone.mobile.ui.model.LoginViewModel
import com.playone.mobile.ui.model.PlayoneDetailViewModel
import com.playone.mobile.ui.model.PlayoneListViewModel
import com.playone.mobile.ui.model.PlayoneViewModel
import com.playone.mobile.ui.model.PlayoneViewModel.Companion.CONTENT_MODE_DETAIL
import com.playone.mobile.ui.navigateToActivityWithResult
import com.playone.mobile.ui.view.NavigationDrawerFragment
import com.playone.mobile.ui.view.TransitionHelper
import kotlinx.android.synthetic.main.activity_playone.bottomNavigation
import kotlinx.android.synthetic.main.activity_playone.btnActionCreate
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    companion object {
        const val REQUEST_CODE = 0x201
    }

    @Inject lateinit var navigator: Navigator

    @Inject lateinit var viewModelFactory: PlayoneListViewModel.PlayoneListViewModelFactory
    @Inject lateinit var loginViewModelFactory: LoginViewModel.LoginViewModelFactory

    @Inject lateinit var detailViewModelFactory: PlayoneDetailViewModel.PlayoneDetailViewModelFactory

    private lateinit var viewModel: PlayoneListViewModel
    private lateinit var loginViewModel: LoginViewModel

    private val navigationDrawer = NavigationDrawerFragment.create(R.menu.activity_playone_drawer)

    val createActionListener by lazy {
        View.OnClickListener {
            val options = TransitionHelper.makeOptionsCompat(
                this).toBundle()
            options?.let {
                val cx = (btnActionCreate.left + btnActionCreate.width / 2) +
                         btnActionCreate.translationX.toInt()
                val cy = btnActionCreate.top + btnActionCreate.height / 2 +
                         btnActionCreate.translationY.toInt()
                navigator.navigateToActivityWithResult<CreatePlayoneActivity>(
                    context = this@PlayoneActivity,
                    resultCode = REQUEST_CODE,
                    options = it) {
                    this.putExtra(EXTRA_CIRCULAR_REVEAL_X, cx)
                    this.putExtra(EXTRA_CIRCULAR_REVEAL_Y, cy)
                }
            }
        }
    }

    val joinActionListener by lazy {
        View.OnClickListener {

        }
    }

    override fun getLayoutId() = R.layout.activity_playone

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionHelper.excludeEnterTarget(this, R.id.action_bar, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.statusBarBackground, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.navigationBarBackground, true)
        }

        ViewModelProviders.of(this, PlayoneViewModel.PlayoneViewModelFactory())
            .get(PlayoneViewModel::class.java).apply {
                observeContentMode(this@PlayoneActivity, Observer {
                    it?.apply(::onChangeContentMode)
                })
            }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PlayoneListViewModel::class.java)

        ViewModelProviders.of(this, detailViewModelFactory)
            .get(PlayoneDetailViewModel::class.java)

        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory)
            .get(LoginViewModel::class.java).apply {
                observeIsSignedIn(this@PlayoneActivity, Observer {

                })

                observeGetCurrentUser(this@PlayoneActivity, Observer {
                    it?.let { user ->
                        user.isVerified.ifTrue {
                            navigationDrawer.arguments?.apply {
                                putSerializable(NavigationDrawerFragment.ARGS_USER_INFO, user)
                            }
                        }
                    }
                })

                getCurrentUser()
                lifecycle::addObserver
            }

        navigator.navigateToFragment(this) {
            replace(R.id.list_content, PlayoneListFragment.newInstance())
        }

        // UI component setting up
        setSupportActionBar(bottomNavigation)
        btnActionCreate.setOnClickListener(createActionListener)

        navigationDrawer.navigationItemSelectedListener = {
            when(it) {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item?.let {
            when (it.itemId) {
                android.R.id.home -> {
                    navigationDrawer.show(
                            supportFragmentManager,
                            NavigationDrawerFragment::class.java.name)
                }
                R.id.app_bar_fav -> toast("Favorite")
                R.id.app_bar_search -> {
                    // TODO: Just for developing
                    toast("sendEmailVerification")
                    loginViewModel.sendEmailVerification()
                }
                R.id.action_sign_out -> {
                    toast("Signing out")
                    FirebaseAuth.getInstance().signOut()
                    finish()
                }
                else -> {
                    return false
                }
            }
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (resultCode) {
            Activity.RESULT_OK -> {
                viewModel.load()
            }
        }
    }

    override fun onDestroy() {

        super.onDestroy()
        navigationDrawer.destroy()
    }

    private fun onSetupListContentMode() {
        btnActionCreate.setOnClickListener(createActionListener)
//        btnActionCreate.
    }

    private fun onSetupDetailContentMode() {
        btnActionCreate.setOnClickListener(joinActionListener)
    }

    fun onChangeContentMode(mode: Int) {
        val transitionY =
            if (mode == CONTENT_MODE_DETAIL) {
                onSetupDetailContentMode()
                bottomNavigation.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                bottomNavigation.isEnabled = false
                bottomNavigation.measuredHeight.toFloat()
            }
            else {
                onSetupListContentMode()
                bottomNavigation.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                bottomNavigation.isEnabled = true
                0f
            }

        val animator =
            ObjectAnimator.ofFloat(bottomNavigation,
                                   "translationY",
                                   transitionY)
        animator.duration = 300
        animator.start()
    }

}