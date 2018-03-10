package com.playone.mobile.ui

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ui.ext.start
import com.playone.mobile.ui.ext.transact

/**
 * Navigator is responsible for navigating as to a Fragment or a Activity
 * and dealing with transition behaviors e.g., transition animation.
 */
class Navigator constructor(activityContext: Context) {

    /**
     * navigate to a Fragment with [AppCompatActivity] and the transactions
     * in [FragmentTransaction]
     */
    fun navigateToFragment(
        context: AppCompatActivity,
        transactions: FragmentTransaction.() -> Unit
    ) {
        context.transact {
            transactions()
        }
    }
}

inline fun <reified T : AppCompatActivity> Navigator.navigateToActivity(
    context: AppCompatActivity,
    crossinline intent: Intent.() -> Unit = {}
) = context.start<T> {
    intent()
}

inline fun <reified T : AppCompatActivity> Navigator.navigateToActivity(
    fragment: Fragment,
    crossinline intent: Intent.() -> Unit = {}
) = (fragment.activity)?.let {
    (fragment.activity as? AppCompatActivity)?.let { it.start<T> { intent() } }
}