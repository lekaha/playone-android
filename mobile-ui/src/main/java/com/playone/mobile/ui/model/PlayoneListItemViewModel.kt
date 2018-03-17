package com.playone.mobile.ui.model

import kotlin.properties.Delegates

class PlayoneListItemViewModel {
    var joinedNumber by Delegates.notNull<Int>()
    var totalNumber = 0

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

    companion object {
        const val DISPLAY_TYPE_PLAYONE: Int = 4
    }

    override fun toString() =
        "id: $id, name: $name, totalNumber: $totalNumber, description: $description, data: $date, updated: $updated, address: $address, longitude: $longitude, latitude: $latitude, limit: $limit, level: $level, host: $host, userId: $userId"

}