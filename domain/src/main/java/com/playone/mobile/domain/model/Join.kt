package com.playone.mobile.domain.model

sealed class Join {
    class Request(
            val joinPlayoneId: String,
            val myUserId: String,
            val message: String): Join()

    class Response(
            val joinPlayoneId: String,
            val yourUserId: String,
            val isAccept: Boolean): Join()
}