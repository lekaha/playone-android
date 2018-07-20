package com.playone.mobile.ui.user

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.injection.module.GlideApp
import com.playone.mobile.ui.model.UserProfileViewModel
import com.playone.mobile.ui.view.CircleViewOutlineProvider
import com.playone.mobile.ui.view.supportsLollipop

class UserProfileFragment: BaseFragment() {

    companion object {
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"

        fun newInstance(userId: String) =
            UserProfileFragment().apply {
                arguments = bundleOf(EXTRA_USER_ID to userId)
            }
    }

    private lateinit var viewModel: UserProfileViewModel

    private val profileUserId
        by lazy { arguments?.takeIf { it.isNotNull() }?.let {it.getString(EXTRA_USER_ID)} ?: "" }

    override fun getLayoutId() = R.layout.fragment_user_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        appCompatActivity?.let {
            initViewModel(it)
        }
    }

    private fun initViewModel(activity: AppCompatActivity) {
        viewModel = ViewModelProviders.of(activity).get(UserProfileViewModel::class.java)
            .apply {
                profileUserId.isEmpty().ifTrue {
                    getCurrentUser()
                } otherwise {
                    getUserById(profileUserId)
                }
            }
    }

    private fun onBindHeadView(imageView: ImageView, imageUrl: String) {

        supportsLollipop {
            imageView.outlineProvider = CircleViewOutlineProvider()
            imageView.clipToOutline = true
        }

        GlideApp.with(this)
            .load(imageUrl)
            .error(android.R.drawable.sym_def_app_icon)
            .into(imageView)
    }
}