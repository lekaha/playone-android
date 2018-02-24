package com.playone.mobile.ui.mapper

import com.playone.mobile.presentation.model.BufferooView
import com.playone.mobile.ui.model.BufferooViewModel
import com.playone.mobile.ui.model.BufferooViewModel.Companion.DISPLAY_TYPE_BROWSE
import com.playone.mobile.ui.view.recycler.DisplayableItem.Companion.toDisplayableItem
import io.reactivex.Observable

/**
 * Map a [BufferooView] to and from a [BufferooViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class BufferooMapper : Mapper<BufferooViewModel, BufferooView> {

    /**
     * Map a [BufferooView] instance to a [BufferooViewModel] instance
     */
    override fun mapToViewModel(type: BufferooView) =
        BufferooViewModel(type.name, type.title, type.avatar)

    @Throws(Exception::class)
    fun mapToViewModels(views: List<BufferooView>) = Observable.fromIterable(views)
            .map { mapToViewModel(it) }
            .map { wrapInDisplayableItem(it) }
            .toList()
            .blockingGet()

    private fun wrapInDisplayableItem(viewEntity: BufferooViewModel) =
        toDisplayableItem(viewEntity, DISPLAY_TYPE_BROWSE)
}