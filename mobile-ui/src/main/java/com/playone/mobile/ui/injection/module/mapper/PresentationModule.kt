package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.mapper.PlayoneMapper
import com.playone.mobile.presentation.mapper.UserViewMapper
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.presentation.model.UserView
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class PresentationModule {

    @Provides
    internal fun proideUserMapper(modelMapper: ModelMapper): Mapper<UserView, User> =
        UserViewMapper(modelMapper)

    @Provides
    internal fun proidePlayoneMapper(modelMapper: ModelMapper): Mapper<PlayoneView, Playone> =
        PlayoneMapper(modelMapper)
}