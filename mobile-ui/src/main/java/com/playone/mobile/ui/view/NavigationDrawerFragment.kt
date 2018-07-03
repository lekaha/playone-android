package com.playone.mobile.ui.view

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.MenuRes
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.contains
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.ui.R
import com.playone.mobile.ui.databinding.FragmentNavigationDrawerBinding
import com.playone.mobile.ui.injection.module.GlideApp
import kotlinx.android.synthetic.main.fragment_navigation_drawer.navigationView

class NavigationDrawerFragment: BottomSheetDialogFragment() {

    companion object {
        private const val ARGS_MENU_ID = "ARGS_MENU_ID"
        internal const val ARGS_USER_INFO = "ARGS_USER_INFO"

        fun create(@MenuRes menuId: Int) =
                NavigationDrawerFragment().apply {
                    arguments = bundleOf(ARGS_MENU_ID to menuId)
                }
    }

    private lateinit var navigationDrawerBinding: FragmentNavigationDrawerBinding

    var navigationItemSelectedListener: ((MenuItem) -> Boolean)? = null

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) =
            DataBindingUtil.inflate<FragmentNavigationDrawerBinding>(
                inflater,
                R.layout.fragment_navigation_drawer,
                container,
                false).let {
                navigationDrawerBinding = it

                it.root
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            navigationView.inflateMenu(it[ARGS_MENU_ID] as Int)
            navigationView.setNavigationItemSelectedListener { menuItem ->
                navigationItemSelectedListener?.let {
                    it(menuItem)
                }

                true
            }

            it[ARGS_USER_INFO]?.let {
                onSignedIn(it as UserView)
            } ?: kotlin.run {
                onUnsignedIn()
            }
        }

//        TODO: should observe the state of BottomSheetBehavior?
//        BottomSheetBehavior.from()
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

    private fun onBindSignedInView(root: View?, userView: UserView) {
        root?.findViewById<ImageView>(R.id.head)?.let {
            onBindHeadView(it, userView.pictureURL)
        }
        root?.findViewById<TextView>(R.id.name)?.text = userView.name
        root?.findViewById<TextView>(R.id.email)?.text = userView.email
    }

    private fun onSignedIn(userView: UserView) {

        view?.takeIf {it is ViewGroup}
            ?.run {
                navigationDrawerBinding.viewStubSigned.viewStub?.also {
                    if (it.parent == null && (view as ViewGroup).contains(it.rootView)) {
                        return
                    }

                    navigationDrawerBinding.viewStubUnsigned.viewStub?.also {
                        if (it.parent == null && (view as ViewGroup).contains(it.rootView)) {
                            // inflated
                            (view as ViewGroup).removeView(it.rootView)
                        }
                    }

                    navigationDrawerBinding.viewStubSigned.viewStub?.inflate()
                        .let { onBindSignedInView(it, userView) }
                }
            }
    }

    private fun onUnsignedIn() {

        view?.takeIf {it is ViewGroup}
            ?.run {
                navigationDrawerBinding.viewStubUnsigned.viewStub?.also {
                    if (it.parent == null && (view as ViewGroup).contains(it.rootView)) {
                        return
                    }

                    navigationDrawerBinding.viewStubSigned.viewStub?.also {
                        if (it.parent == null && (view as ViewGroup).contains(it.rootView)) {
                            // inflated
                            (view as ViewGroup).removeView(it.rootView)
                        }
                    }

                    navigationDrawerBinding.viewStubUnsigned.viewStub?.inflate()
                }
            }
    }

    fun destroy() {

        navigationItemSelectedListener = null
    }
}

inline fun supportsLollipop(code: () -> Unit): Boolean {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
        return true
    }

    return false
}