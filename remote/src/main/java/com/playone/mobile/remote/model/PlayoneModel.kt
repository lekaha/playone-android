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
    private var _id: String = DEFAULT_STR,
    private var _name: String = DEFAULT_STR,
    private var _description: String = DEFAULT_STR,
    private var _date: Long = DEFAULT_LONG,
    private var _updated: Long = DEFAULT_LONG,
    private var _address: String = DEFAULT_STR,
    private var _longitude: Double = DEFAULT_DOUBLE,
    private var _latitude: Double = DEFAULT_DOUBLE,
    private var _limit: Int = DEFAULT_INT,
    private var _level: Int = DEFAULT_INT,
    private var _host: String = DEFAULT_STR,
    private var _userId: String = DEFAULT_STR
) {

    var id by NotValidVar(if (_id != DEFAULT_STR) _id else DEFAULT_STR)
    var name by NotValidVar(if (_name != DEFAULT_STR) _name else DEFAULT_STR)
    var description by NotValidVar(if (_description != DEFAULT_STR) _description else DEFAULT_STR)
    var date by NotValidVar(if (_date != DEFAULT_LONG) _date else DEFAULT_LONG)
    var updated by NotValidVar(if (_updated != DEFAULT_LONG) _updated else DEFAULT_LONG)
    var address by NotValidVar(if (_address != DEFAULT_STR) _address else DEFAULT_STR)
    var longitude by NotValidVar(if (_longitude != DEFAULT_DOUBLE) _longitude else DEFAULT_DOUBLE)
    var latitude by NotValidVar(if (_latitude != DEFAULT_DOUBLE) _latitude else DEFAULT_DOUBLE)
    var limit by NotValidVar(if (_limit != DEFAULT_INT) _limit else DEFAULT_INT)
    var level by NotValidVar(if (_level != DEFAULT_INT) _level else DEFAULT_INT)
    var host by NotValidVar(if (_host != DEFAULT_STR) _host else DEFAULT_STR)
    var userId by NotValidVar(if (_userId != DEFAULT_STR) _userId else DEFAULT_STR)

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