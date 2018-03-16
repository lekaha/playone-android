package com.playone.mobile.ui.mapper

import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.model.PlayoneListItemViewModel
import com.playone.mobile.ui.model.PlayoneListItemViewModel.Companion.DISPLAY_TYPE_PLAYONE
import com.playone.mobile.ui.view.recycler.DisplayableItem.Companion.toDisplayableItem
import io.reactivex.Observable
import org.modelmapper.ModelMapper

/**
 * Map a [PlayoneView] to and from a [PlayoneListItemViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class PlayoneMapper(
    private val modelMapper: ModelMapper
) : Mapper<PlayoneListItemViewModel, PlayoneView> {

    /**
     * Map a [PlayoneView] instance to a [PlayoneListItemViewModel] instance
     */
    override fun mapToViewModel(type: PlayoneView) = throw UnsupportedOperationException()

    @Throws(Exception::class)
    fun mapToViewModels(views: List<PlayoneView>) = Observable.fromIterable(views)
        .map { modelMapper.map(it, PlayoneListItemViewModel::class.java) }
        .map { wrapInDisplayableItem(it) }
        .toList()
        .blockingGet()

    private fun wrapInDisplayableItem(viewEntity: PlayoneListItemViewModel) =
        toDisplayableItem(viewEntity, DISPLAY_TYPE_PLAYONE)
}