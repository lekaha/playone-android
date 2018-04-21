package com.playone.mobile.ui.view.recycler

import com.google.auto.value.AutoValue

@AutoValue
abstract class DisplayableItem<out T> {

    companion object {

        fun <T> builder(): Builder<T> = AutoValue_DisplayableItem.Builder()
        fun <T> toDisplayableItem(model: T, type: Int, click: OnItemClickedListener<*> = {}) =
            builder<T>().type(type).model(model).click(click).build()
    }

    abstract fun type(): Int
    abstract fun model(): T
    abstract fun click(): OnItemClickedListener<*>

    @AutoValue.Builder
    abstract class Builder<T> {

        abstract fun type(type: Int): Builder<T>

        abstract fun model(model: T): Builder<T>

        abstract fun click(onClicked: OnItemClickedListener<*> = {}): Builder<T>

        abstract fun build(): DisplayableItem<T>
    }
}

typealias OnItemClickedListener<MODEL> = (model: MODEL) -> Unit