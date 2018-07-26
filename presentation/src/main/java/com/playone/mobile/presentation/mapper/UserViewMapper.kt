package com.playone.mobile.presentation.mapper

import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.model.UserView
import org.modelmapper.ModelMapper

class UserViewMapper(private val modelMapper: ModelMapper) : Mapper<UserView, User> {

    override fun mapToView(type: User) =
        UserView().apply {
            name = type.name
            email = type.email
            pictureURL = type.pictureURL
            isVerified = type.isVerified
        }
//        modelMapper.map(type, UserView::class.java)
}