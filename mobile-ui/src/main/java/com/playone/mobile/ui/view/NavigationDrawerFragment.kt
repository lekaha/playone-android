package com.playone.mobile.ui.view

import android.os.Bundle
import android.support.annotation.MenuRes
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.playone.mobile.ui.R
import kotlinx.android.synthetic.main.fragment_navigation_drawer.*

class NavigationDrawerFragment: BottomSheetDialogFragment() {

    companion object {
        private const val ARGS_MENU_ID = "ARGS_MENU_ID"

        fun create(@MenuRes menuId: Int) =
                NavigationDrawerFragment().apply {
                    arguments = bundleOf(ARGS_MENU_ID to menuId)
                }
    }

    var navigationItemSelectedListener: ((MenuItem) -> Boolean)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_navigation_drawer, container, false)

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
        }
    }

    fun destroy() {
        navigationItemSelectedListener = null
    }
}