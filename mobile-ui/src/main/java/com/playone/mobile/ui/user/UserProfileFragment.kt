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
import kotlinx.android.synthetic.main.fragment_user_profile.email
import kotlinx.android.synthetic.main.fragment_user_profile.head
import kotlinx.android.synthetic.main.fragment_user_profile.name

class UserProfileFragment: BaseFragment() {

    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: String) =
            UserProfileFragment().apply {
                arguments = bundleOf(ARG_USER_ID to userId)
            }
    }

    private lateinit var viewModel: UserProfileViewModel

    private val profileUserId
        by lazy { arguments?.takeIf { it.isNotNull() }?.let {it.getString(ARG_USER_ID)} ?: "" }

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

                observe(userProfile, this@UserProfileFragment) {
                    it?.let {
                        name.text = it.name
                        email.text = it.email
                        onBindUserImage(head, it.pictureURL)
                    }
                }
            }
    }

    private fun onBindUserImage(imageView: ImageView, imageUrl: String) {

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