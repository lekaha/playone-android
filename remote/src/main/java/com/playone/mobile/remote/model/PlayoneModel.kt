package com.playone.mobile.remote.model

import kotlin.properties.Delegates

/**
 * Representation for a [PlayoneModel] fetched from the API.
 */
class PlayoneModel {

    // OPTIMIZE(jieyi): 2018/03/10 From the data from firebase must be initialized, otherwise it will throw an error.
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

    override fun toString() =
        "id: $id, name: $name, description: $description, data: $date, updated: $updated, address: $address, longitude: $longitude, latitude: $latitude, limit: $limit, level: $level, host: $host, userId: $userId"

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

    fun copy(
        id: String? = null,
        name: String? = null,
        description: String? = null,
        date: Long? = null,
        updated: Long? = null,
        address: String? = null,
        longitude: Double? = null,
        latitude: Double? = null,
        limit: Int? = null,
        level: Int? = null,
        host: String? = null,
        userId: String? = null
    ) = PlayoneModel().also {
        it.id = id ?: this.id
        it.name = name ?: this.name
        it.description = description ?: this.description
        it.date = date ?: this.date
        it.updated = updated ?: this.updated
        it.address = address ?: this.address
        it.longitude = longitude ?: this.longitude
        it.latitude = latitude ?: this.latitude
        it.limit = limit ?: this.limit
        it.level = level ?: this.level
        it.host = host ?: this.host
        it.userId = userId ?: this.userId
    }
}
