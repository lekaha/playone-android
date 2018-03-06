package com.playone.mobile.presentation.mapper

import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.model.UserView
import org.modelmapper.ModelMapper

class UserMapper(private val modelMapper: ModelMapper) : Mapper<UserView, User> {

    override fun mapToView(type: User) = modelMapper.map(type, UserView::class.java)
}