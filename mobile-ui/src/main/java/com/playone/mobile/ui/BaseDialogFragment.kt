package com.playone.mobile.ui

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        retainInstance = true
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    val appCompatActivity: AppCompatActivity?
        get() {
            val activity = activity
            if (activity is AppCompatActivity) {
                return activity
            }
            return null
        }
}