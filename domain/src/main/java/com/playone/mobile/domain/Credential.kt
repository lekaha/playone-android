package com.playone.mobile.domain

abstract class Credential<out T> {

    abstract fun isSocialNetworkCredential(): Boolean
    abstract fun getContent(): T
}