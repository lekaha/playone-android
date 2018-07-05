package com.playone.mobile.ui.mapper

import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.model.PlayoneListItemViewModel
import com.playone.mobile.ui.model.PlayoneListItemViewModel.Companion.DISPLAY_TYPE_PLAYONE
import com.playone.mobile.ui.view.recycler.DisplayableItem.Companion.toDisplayableItem
import com.playone.mobile.ui.view.recycler.OnItemClickedListener
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
    fun mapToViewModels(
        views: List<PlayoneView>,
        onItemClickedListener: OnItemClickedListener<PlayoneListItemViewModel> = { _, _ -> }
    ) = Observable.fromIterable(views)
        .map { modelMapper.map(it, PlayoneListItemViewModel::class.java) }
        .map { wrapInDisplayableItem(it, onItemClickedListener) }
        .toList()
        .blockingGet()

    private fun wrapInDisplayableItem(
        viewEntity: PlayoneListItemViewModel,
        onItemClickedListener: OnItemClickedListener<PlayoneListItemViewModel>
    ) =
        toDisplayableItem(viewEntity, DISPLAY_TYPE_PLAYONE, onItemClickedListener)

    internal fun mapToView(viewModel: PlayoneListItemViewModel): PlayoneView =
        modelMapper.map(viewModel, PlayoneView::class.java)
}