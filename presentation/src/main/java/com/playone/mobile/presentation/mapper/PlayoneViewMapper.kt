package com.playone.mobile.presentation.mapper

import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.model.PlayoneView
import org.modelmapper.ModelMapper

class PlayoneViewMapper(private val modelMapper: ModelMapper) : Mapper<PlayoneView, Playone> {

    override fun mapToView(type: Playone) = modelMapper.map(type, PlayoneView::class.java)
}