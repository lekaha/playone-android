package com.playone.mobile.ui.playone

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.activity_playone.bottom_navigation
import javax.inject.Inject

class PlayoneActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator

    @Inject lateinit var viewModelFactory: PlayoneListViewModel.PlayoneListViewModelFactory

    private lateinit var viewModel: PlayoneListViewModel

    override fun getLayoutId() = R.layout.activity_playone

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PlayoneListViewModel::class.java)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_sign_out -> {
                    FirebaseAuth.getInstance().signOut()
                    finish()
                    true

                }
                else -> false
            }
        }
    }
}