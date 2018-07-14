package com.playone.mobile.domain.model

import java.util.Date
import kotlin.properties.Delegates

/**
 * Representation for a [Playone] fetched from an external layer data source
 */
sealed class Playone {

    class Detail : Playone() {
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
        lateinit var coverUrl: String
        var isFavorited: Boolean = false
    }

    data class CreateParameters(
        val myUserId: String,
        var name: String,
        var description: String,
        var playoneDate: Date,
        val longitude: Double,
        var latitude: Double,
        var address: String,
        var limitPeople: Int,
        var level: Int,
        val coverImagePath: String
    ) : Playone()

    data class UpdateParameters(
            var playoneId: String,
            val myUserId: String,
            var name: String,
            var description: String,
            var playoneDate: Date,
            val longitude: Double,
            var latitude: Double,
            var address: String,
            var limitPeople: Int,
            var level: Int,
            val coverImagePath: String

    ) : Playone()
}