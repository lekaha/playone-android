package com.playone.mobile.data.model

/**
 * Representation for a [PlayoneEntity] fetched from an external layer data source.
 */
data class PlayoneEntity(
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
) : IPlayoneEntity
