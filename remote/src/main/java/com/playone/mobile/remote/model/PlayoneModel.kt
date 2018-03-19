package com.playone.mobile.remote.model

import com.playone.mobile.common.NotValidVar
import com.playone.mobile.ext.DEFAULT_DOUBLE
import com.playone.mobile.ext.DEFAULT_INT
import com.playone.mobile.ext.DEFAULT_LONG
import com.playone.mobile.ext.DEFAULT_STR

/**
 * Representation for a [PlayoneModel] fetched from the API.
 */
data class PlayoneModel(
    var id: String = DEFAULT_STR,
    var name: String = DEFAULT_STR,
    var description: String = DEFAULT_STR,
    var date: Long = DEFAULT_LONG,
    var updated: Long = DEFAULT_LONG,
    var address: String = DEFAULT_STR,
    var longitude: Double = DEFAULT_DOUBLE,
    var latitude: Double = DEFAULT_DOUBLE,
    var limit: Int = DEFAULT_INT,
    var level: Int = DEFAULT_INT,
    var host: String = DEFAULT_STR,
    var userId: String = DEFAULT_STR
) {

    var _id by NotValidVar(id)
    var _name by NotValidVar(name)
    var _description by NotValidVar(description)
    var _date by NotValidVar(date)
    var _updated by NotValidVar(updated)
    var _address by NotValidVar(address)
    var _longitude by NotValidVar(longitude)
    var _latitude by NotValidVar(latitude)
    var _limit by NotValidVar(limit)
    var _level by NotValidVar(level)
    var _host by NotValidVar(host)
    var _userId by NotValidVar(userId)

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