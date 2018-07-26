package com.playone.mobile.ui.user

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.ext.newIntent
import com.playone.mobile.ui.model.UserProfileViewModel
import javax.inject.Inject

class UserProfileActivity : BaseActivity() {

    companion object {
        private const val EXTRA_IS_CURRENT_USER = "EXTRA_IS_CURRENT_USER"
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"

        fun create(activity: BaseActivity, userId: String = "") =
            newIntent<UserProfileActivity>(activity).apply {
                putExtra(EXTRA_IS_CURRENT_USER, userId.isEmpty())
                putExtra(EXTRA_USER_ID, userId)
            }
    }

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var userProfileViewModelFactory:
        UserProfileViewModel.UserProfileViewModelFactory

    override fun getLayoutId() = R.layout.activity_browse

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        val userId = intent.getStringExtra(EXTRA_USER_ID)
        ViewModelProviders.of(this, userProfileViewModelFactory)
            .get(UserProfileViewModel::class.java).apply {
                navigator.navigateToFragment(this@UserProfileActivity) {
                    replace(R.id.content, UserProfileFragment.newInstance(userId))
                }
            }
    }
}