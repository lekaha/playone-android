package com.playone.mobile.domain.model

import kotlin.properties.Delegates

/**
 * Representation for a [Playone] fetched from an external layer data source
 */
class Playone {

    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    var date by Delegates.notNull<Long>()
    var updated by Delegates.notNull<Long>()
    lateinit var address: String
    var longitude by Delegates.notNull<Double>()
    var latitude by Delegates.notNull<Double>()
    var limit by Delegates.notNull<Int>()
    var level by Delegates.notNull<Int>()
    lateinit var host: String
    lateinit var userId: String
}