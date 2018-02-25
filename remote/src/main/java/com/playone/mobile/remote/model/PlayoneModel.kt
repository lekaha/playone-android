package com.playone.mobile.remote.model

/**
 * Representation for a [PlayoneModel] fetched from the API.
 */
data class PlayoneModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var date: Long = 0,
    var updated: Long = 0,
    var address: String = "",
    var longitude: Double = .0,
    var latitude: Double = .0,
    var limit: Int = 0,
    var level: Int = 0,
    var host: String = "",
    var userId: String = ""
) {
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
