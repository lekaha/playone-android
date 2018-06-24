package com.playone.mobile.ui.playone

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.toast
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.create.CreatePlayoneActivity
import com.playone.mobile.ui.create.CreatePlayoneActivity.Companion.EXTRA_CIRCULAR_REVEAL_X
import com.playone.mobile.ui.create.CreatePlayoneActivity.Companion.EXTRA_CIRCULAR_REVEAL_Y
import com.playone.mobile.ui.model.LoginViewModel
import com.playone.mobile.ui.model.PlayoneListViewModel
import com.playone.mobile.ui.navigateToActivityWithResult
import com.playone.mobile.ui.view.NavigationDrawerFragment
import com.playone.mobile.ui.view.TransitionHelper
import kotlinx.android.synthetic.main.activity_playone.*
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator

    @Inject lateinit var viewModelFactory: PlayoneListViewModel.PlayoneListViewModelFactory
    @Inject lateinit var loginViewModelFactory: LoginViewModel.LoginViewModelFactory

    private lateinit var viewModel: PlayoneListViewModel
    private lateinit var loginViewModel: LoginViewModel

    private val navigationDrawer = NavigationDrawerFragment.create(R.menu.activity_playone_drawer)

    override fun getLayoutId() = R.layout.activity_playone

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionHelper.excludeEnterTarget(this, R.id.action_bar, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.statusBarBackground, true)
            TransitionHelper.excludeEnterTarget(this, android.R.id.navigationBarBackground, true)
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PlayoneListViewModel::class.java)

        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory)
            .get(LoginViewModel::class.java)

        navigator.navigateToFragment(this) {
            replace(R.id.list_content, PlayoneListFragment.newInstance())
        }

        // UI component setting up
        setSupportActionBar(bottomNavigation)
        btnActionCreate.setOnClickListener {
            val options = TransitionHelper.makeOptionsCompat(
                this).toBundle()
            options?.let {
                val cx = btnActionCreate.left + btnActionCreate.width / 2
                val cy = btnActionCreate.top + btnActionCreate.height / 2

                navigator.navigateToActivityWithResult<CreatePlayoneActivity>(
                    context = this@PlayoneActivity,
                    options = it) {
                    this.putExtra(EXTRA_CIRCULAR_REVEAL_X, cx)
                    this.putExtra(EXTRA_CIRCULAR_REVEAL_Y, cy)
                }
            }
        }

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
                    toast("Menu")
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

    override fun onDestroy() {
        super.onDestroy()
        navigationDrawer.destroy()
    }
}