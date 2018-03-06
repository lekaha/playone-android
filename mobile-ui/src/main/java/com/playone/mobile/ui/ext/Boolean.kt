package com.playone.mobile.ui.ext

import com.playone.mobile.ext.isNotNull
import com.playone.mobile.ext.isNull

inline fun Boolean?.ifTrue(block: () -> Unit): Boolean {

    if (this.isNotNull() and this!!) {
        block()
    }

    return true
}

inline fun Boolean?.ifFalse(block: () -> Unit): Boolean {

    if (this.isNull() or this!!.not()) {
        block()
    }

    return false
}

inline infix fun Boolean.otherwise(block: () -> Unit) {

    if (this.not()) {
        block()
    }
}