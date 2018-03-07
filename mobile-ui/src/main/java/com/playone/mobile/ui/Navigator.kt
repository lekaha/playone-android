package com.playone.mobile.ui

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
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
    fun navigateTo(context: AppCompatActivity, transactions: FragmentTransaction.() -> Unit) {
        context.transact {
            transactions()
        }
    }
}
