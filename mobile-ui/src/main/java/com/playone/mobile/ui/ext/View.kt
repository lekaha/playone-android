package com.playone.mobile.ui.ext

import android.view.View
import android.view.ViewTreeObserver

inline fun <T: View> T.afterMeasured(crossinline block: T.() -> Unit) =
    with(viewTreeObserver) {
        addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    block()
                }
            }
        })
    }