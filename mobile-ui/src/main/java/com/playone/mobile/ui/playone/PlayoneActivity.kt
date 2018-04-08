package com.playone.mobile.ui.playone

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.create.CreatePlayoneActivity
import com.playone.mobile.ui.model.PlayoneDetailViewModel
import com.playone.mobile.ui.model.PlayoneListViewModel
import com.playone.mobile.ui.navigateToActivityWithResult
import com.playone.mobile.ui.view.TransitionHelper
import kotlinx.android.synthetic.main.activity_playone.bottom_navigation
import kotlinx.android.synthetic.main.activity_playone.button_create
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator

    @Inject lateinit var viewModelFactory: PlayoneListViewModel.PlayoneListViewModelFactory

    @Inject lateinit var detailViewModelFactory: PlayoneDetailViewModel.PlayoneDetailViewModelFactory

    private lateinit var viewModel: PlayoneListViewModel

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

        ViewModelProviders.of(this, detailViewModelFactory)
            .get(PlayoneDetailViewModel::class.java)

        navigator.navigateToFragment(this, {
            replace(R.id.list_content, PlayoneListFragment.newInstance())
        })

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_sign_out -> {
                    FirebaseAuth.getInstance().signOut()
                    finish()
                    true

                }
                else -> false
            }
        }

        button_create.setOnClickListener {
            val options = TransitionHelper.makeOptionsCompat(
                this).toBundle()
            options?.let {
                val cx = button_create.left + button_create.width / 2
                val cy = button_create.top + button_create.height / 2

                navigator.navigateToActivityWithResult<CreatePlayoneActivity>(
                    context = this@PlayoneActivity,
                    options = it) {
                    this.putExtra("CX", cx)
                    this.putExtra("CY", cy)
//                    this@PlayoneActivity.overridePendingTransition(R.anim.scale_up, R.anim.scale_down)
                }
            }
        }
    }

    fun gotoFragment(fragmentName: String, params: HashMap<String, Any>) {

        when (fragmentName) {
            PlayoneDetailFragment::class.java.simpleName -> {
                navigator.navigateToFragment(this) {
                    replace(R.id.list_content,
                            PlayoneDetailFragment.newInstance(params[fragmentName] as String),
                            fragmentName)
                    addToBackStack(null)
                }
            }
        }
    }
}