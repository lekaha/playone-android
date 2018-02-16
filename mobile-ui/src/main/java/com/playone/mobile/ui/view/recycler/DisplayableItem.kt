package com.playone.mobile.ui.view.recycler

import com.google.auto.value.AutoValue

@AutoValue
abstract class DisplayableItem<out T> {

    companion object {
        fun <T> builder(): Builder<T> = AutoValue_DisplayableItem.Builder()

        fun toDisplayableItem(model: Any, type: Int) =
            builder<Any>().type(type).model(model).build()
    }

    abstract fun type(): Int
    abstract fun model(): T

    @AutoValue.Builder
    abstract class Builder<T> {

        abstract fun type(type: Int): Builder<T>

        abstract fun model(model: T): Builder<T>

        abstract fun build(): DisplayableItem<T>
    }
}