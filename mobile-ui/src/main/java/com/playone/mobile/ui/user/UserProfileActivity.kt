package com.playone.mobile.ui.user

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.UserProfileViewModel
import javax.inject.Inject

class UserProfileActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var userProfileViewModelFactory:
        UserProfileViewModel.UserProfileViewModelFactory

    override fun getLayoutId() = R.layout.activity_browse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProviders.of(this, userProfileViewModelFactory)
            .get(UserProfileViewModel::class.java).apply {
                navigator.navigateToFragment(this@UserProfileActivity) {
                    replace(R.id.list_content, UserProfileFragment.newInstance())
                }
            }
    }
}