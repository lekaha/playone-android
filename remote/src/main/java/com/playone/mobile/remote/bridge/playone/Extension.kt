package com.playone.mobile.remote.bridge.playone

import io.reactivex.SingleEmitter

fun <T> SingleEmitter<T>.errorHandler(code: Int, msg: String, detail: String) {
    onError(Exception("error code: $code, msg: $msg, detail: $detail"))
}

fun <T> SingleEmitter<T>.errorNullObject() {
    onError(Exception("object is null."))
}