package com.playone.mobile.presentation.model

import com.playone.mobile.ext.DEFAULT_DOUBLE
import com.playone.mobile.ext.DEFAULT_INT
import com.playone.mobile.ext.DEFAULT_LONG
import java.io.Serializable
import kotlin.properties.Delegates

class PlayoneView: Serializable {

    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    var date: Long = DEFAULT_LONG
    var updated: Long = DEFAULT_LONG
    lateinit var address: String
    var longitude: Double = DEFAULT_DOUBLE
    var latitude: Double = DEFAULT_DOUBLE
    var limit: Int = DEFAULT_INT
    var level: Int = DEFAULT_INT
    lateinit var host: String
    lateinit var userId: String
    var isFavorited: Boolean = false
}