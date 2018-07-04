package com.playone.mobile.ui.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise

fun AppCompatActivity.checkResolveActivity(intent: Intent) =
    intent.resolveActivity(packageManager) != null

inline fun <reified T : Any> AppCompatActivity.startForResult(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    checkResolveActivity(intent).ifTrue {
        startActivityForResult(intent, requestCode)
    }
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
        checkResolveActivity(intent).ifTrue {
            startActivityForResult(intent, requestCode, options)
        }
    } otherwise {
        checkResolveActivity(intent).ifTrue {
            startActivityForResult(intent, requestCode)
        }
    }


}

inline fun <reified T : Any> AppCompatActivity.startWithUri(
    action: String,
    uri: Uri,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(action, uri)
    intent.init()
    checkResolveActivity(intent).ifTrue {
        startActivity(intent)
    }
}

inline fun <reified T : Any> AppCompatActivity.start(
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    checkResolveActivity(intent).ifTrue {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context) = Intent(context, T::class.java)

inline fun <reified T : Any> newIntent(action: String, uri: Uri) = Intent(action, uri)

inline fun AppCompatActivity.transact(transactions: FragmentTransaction.() -> Unit) =
    supportFragmentManager.beginTransaction().apply { transactions() }.commit()