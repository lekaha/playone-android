package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.ui.mapper.PlayoneMapper
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class ViewModule {

    @Provides
    @JvmSuppressWildcards
    internal fun providePlayoneMapper(
        modelMapper: ModelMapper
    ) = PlayoneMapper(modelMapper)
}