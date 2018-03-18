package com.playone.mobile.remote.model

import com.playone.mobile.common.NotValidVar
import com.playone.mobile.ext.defaultDouble
import com.playone.mobile.ext.defaultInt
import com.playone.mobile.ext.defaultLong
import com.playone.mobile.ext.defaultStr

/**
 * Representation for a [PlayoneModel] fetched from the API.
 */
data class PlayoneModel(
    var id: String = defaultStr,
    var name: String = defaultStr,
    var description: String = defaultStr,
    var date: Long = defaultLong,
    var updated: Long = defaultLong,
    var address: String = defaultStr,
    var longitude: Double = defaultDouble,
    var latitude: Double = defaultDouble,
    var limit: Int = defaultInt,
    var level: Int = defaultInt,
    var host: String = defaultStr,
    var userId: String = defaultStr
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