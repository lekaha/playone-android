package com.playone.mobile.ui.ext

fun isCapableApi(api: Int, block: () -> Unit = {}) =

    if (api <= android.os.Build.VERSION.SDK_INT) {
        block()
        true
    }
    else {
        false
    }