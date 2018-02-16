package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.browse.GetBufferoos
import com.playone.mobile.presentation.browse.BrowseBufferoosContract
import com.playone.mobile.presentation.browse.BrowseBufferoosPresenter
import com.playone.mobile.presentation.mapper.BufferooMapper
import com.playone.mobile.ui.model.BrowseViewModelFactory
import dagger.Module
import dagger.Provides


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
open class BrowseActivityModule {

    @Provides
    internal fun provideBrowsePresenter(getBufferoos: GetBufferoos, mapper: BufferooMapper):
            BrowseBufferoosContract.Presenter = BrowseBufferoosPresenter(getBufferoos, mapper)

    @Provides
    internal fun provideBrowseViewModelFactory(presenter: BrowseBufferoosContract.Presenter)
            = BrowseViewModelFactory(presenter)
}
