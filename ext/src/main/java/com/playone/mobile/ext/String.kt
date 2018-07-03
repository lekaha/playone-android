package com.playone.mobile.ext

public inline fun String?.orString(str: String): String = this?.takeIf { it.isNotEmpty() } ?: str

public inline fun String.toIntOrZero() = toIntOrNull() ?: 0