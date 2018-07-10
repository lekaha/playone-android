package com.playone.mobile.ui.ext

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise

inline fun <reified T : Any> Fragment.startForResult(
    requestCode: Int = -1,
    options: Bundle,
    action: String? = null,
    uri: Uri? = null,
    noinline init: Intent.() -> Unit = {}
) {
    activity?.let { context ->
        val intent = action?.let {
            newIntent<T>(action, uri)
        } ?: newIntent<T>(context).apply {
            init()
        }

        isCapableApi(Build.VERSION_CODES.JELLY_BEAN_MR1) {
            checkResolveActivity(intent, context.packageManager).ifTrue {
                startActivityForResult(intent, requestCode, options)
            }
        } otherwise {
            checkResolveActivity(intent, context.packageManager).ifTrue {
                startActivityForResult(intent, requestCode)
            }
        }
    }
}

inline fun <reified T : Any> Fragment.start(
    noinline init: Intent.() -> Unit = {}
) {
    activity?.let { context ->
        val intent = newIntent<T>(context)
        intent.init()
        checkResolveActivity(intent, context.packageManager).ifTrue {
            startActivity(intent)
        }
    }
}