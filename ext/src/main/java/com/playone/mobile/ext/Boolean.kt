package com.playone.mobile.ext

inline fun Boolean?.ifTrue(block: () -> Unit): Boolean {

    if (this.isNotNull()) {
        if (this!!) {
            block()
            return true
        }
    }

    return false
}

inline fun Boolean?.ifFalse(block: () -> Unit): Boolean {

    if (this.isNull()) {
        block()
        return true
    } else if (this!!.not()) {
        block()
        return true
    }

    return false
}

inline infix fun Boolean.otherwise(block: () -> Unit) {

    if (this.not()) {
        block()
    }
}