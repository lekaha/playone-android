package com.playone.mobile.remote

import com.playone.mobile.remote.service.ServiceFactory

/**
 * Provide "make" methods to create instances of [PlayoneService]
 */
object PlayoneServiceFactory : ServiceFactory<PlayoneService> {

    override fun makeService(isDebug: Boolean): PlayoneService {
        throw UnsupportedOperationException()
    }

    fun makeFirebaseService(isDebug: Boolean, playoneFirebase: PlayoneFirebase)
            = PlayoneServiceFirebaseImpl(playoneFirebase)
}