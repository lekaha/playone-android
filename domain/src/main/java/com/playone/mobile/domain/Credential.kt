package com.playone.mobile.domain

abstract class Credential {

    abstract fun isSocialNetworkCredential(): Boolean
    abstract fun <R> getContent(): R
}