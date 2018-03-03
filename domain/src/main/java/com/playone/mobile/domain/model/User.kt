package com.playone.mobile.domain.model

/**
 * Representation for a [User] fetched from an external layer data source
 */
data class User(
    val name: String = "",
    val title: String = "",
    val avatar: String = ""
)