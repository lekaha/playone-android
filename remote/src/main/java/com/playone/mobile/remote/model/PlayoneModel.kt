package com.playone.mobile.remote.model

import kotlin.properties.Delegates

/**
 * Representation for a [PlayoneModel] fetched from the API.
 */
class PlayoneModel {

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

    fun toMap() = hashMapOf("name" to name,
                            "name" to name,
                            "description" to description,
                            "host" to host,
                            "date" to date,
                            "updated" to updated,
                            "address" to address,
                            "latitude" to latitude,
                            "longitude" to longitude,
                            "limit" to limit,
                            "level" to level,
                            "userId" to userId)
}
