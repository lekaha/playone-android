package com.playone.mobile.ui.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.playone.mobile.ext.otherwise

inline fun <reified T : Any> AppCompatActivity.startForResult(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode)
}

@SuppressLint("RestrictedApi")
inline fun <reified T : Any> AppCompatActivity.startForResult(
    requestCode: Int = -1,
    options: Bundle,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()

    isCapableApi(android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        startActivityForResult(intent, requestCode, options)
    } otherwise {
        startActivityForResult(intent, requestCode)
    }


}

inline fun <reified T : Any> AppCompatActivity.start(
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Any> newIntent(context: Context) = Intent(context, T::class.java)

inline fun AppCompatActivity.transact(transactions: FragmentTransaction.() -> Unit) =
    supportFragmentManager.beginTransaction().apply { transactions() }.commit()