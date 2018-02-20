package com.playone.mobile.remote.service

/**
 * ServiceFactory
 */
interface ServiceFactory<out S> {
    fun makeService(isDebug: Boolean): S
}