package com.playone.mobile.data.model

/**
 * Representation for a [PlayoneEntity] fetched from an external layer data source.
 */
sealed class PlayoneEntity {

    data class Create(
        val name: String,
        val description: String,
        val date: Long,
        val address: String,
        var longitude: Double,
        var latitude: Double,
        var limit: Int,
        var level: Int,
        val host: String,
        val coverPath: String
    ) : PlayoneEntity()

    data class Entity(
        val id: String,
        val name: String,
        val description: String,
        var date: Long,
        var updated: Long,
        val address: String,
        var longitude: Double,
        var latitude: Double,
        var limit: Int,
        var level: Int,
        val host: String,
        val userId: String,
        val coverUrl: String
    ) : PlayoneEntity()
}
