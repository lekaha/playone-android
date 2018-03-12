package com.playone.mobile.common

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class NotValidVar<T : Any>(private val invalidVal: T) : ReadWriteProperty<Any?, T> {
    private var value: T = invalidVal

    public override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        if (value != invalidVal)
            value
        else
            throw IllegalStateException("Property ${property.name} should be initialized before get.")

    public override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}