package com.playone.mobile.ui.ext

inline fun Boolean.ifTrue(block: () -> Unit): Boolean {

    if (this) {
        block()
    }

    return this
}

inline fun Boolean.ifFalse(block: () -> Unit): Boolean {

    if (!this) {
        block()
    }

    return this
}