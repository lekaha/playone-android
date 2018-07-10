package com.playone.mobile.ui.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise

inline fun checkResolveActivity(intent: Intent, pm: PackageManager) =
    intent.resolveActivity(pm) != null

inline fun <reified T : Any> AppCompatActivity.startForResult(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    checkResolveActivity(intent, packageManager).ifTrue {
        startActivityForResult(intent, requestCode)
    }
}

@SuppressLint("RestrictedApi")
inline fun <reified T : Any> AppCompatActivity.startForResult(
    requestCode: Int = -1,
    options: Bundle,
    action: String? = null,
    uri: Uri? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = action?.let {
        newIntent<T>(action, uri)
    } ?: run {
        newIntent<T>(this).apply {
            init()
        }
    }

    isCapableApi(android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        checkResolveActivity(intent, packageManager).ifTrue {
            startActivityForResult(intent, requestCode, options)
        }
    } otherwise {
        checkResolveActivity(intent, packageManager).ifTrue {
            startActivityForResult(intent, requestCode)
        }
    }
}

inline fun <reified T : Any> AppCompatActivity.start(
    action: String,
    uri: Uri? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(action, uri)
    intent.init()
    checkResolveActivity(intent, packageManager).ifTrue {
        startActivity(intent)
    }
}

inline fun <reified T : Any> AppCompatActivity.start(
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    checkResolveActivity(intent, packageManager).ifTrue {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context) = Intent(context, T::class.java)

inline fun <reified T : Any> newIntent(action: String, uri: Uri? = null) =
    uri?.let { Intent(action, it) } ?: Intent(action)

inline fun AppCompatActivity.transact(transactions: FragmentTransaction.() -> Unit) =
    supportFragmentManager.beginTransaction().apply { transactions() }.commit()