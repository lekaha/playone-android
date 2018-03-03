package com.playone.mobile.domain.model

/**
 * Representation for a [Playone] fetched from an external layer data source
 */
data class Playone(
    val name: String = "",
    val title: String = "",
    val avatar: String = ""
)