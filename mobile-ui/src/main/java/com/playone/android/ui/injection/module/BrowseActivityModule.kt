package com.playone.android.ui.injection.module

import com.playone.android.domain.interactor.browse.GetBufferoos
import com.playone.android.presentation.browse.BrowseBufferoosContract
import com.playone.android.presentation.browse.BrowseBufferoosPresenter
import com.playone.android.presentation.mapper.BufferooMapper
import com.playone.android.ui.model.BrowseViewModelFactory
import dagger.Module
import dagger.Provides


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
open class BrowseActivityModule {

    @Provides
    internal fun provideBrowsePresenter(getBufferoos: GetBufferoos, mapper: BufferooMapper):
            BrowseBufferoosContract.Presenter {
        return BrowseBufferoosPresenter(getBufferoos, mapper)
    }

    @Provides
    internal fun provideBrowseViewModelFactory(presenter: BrowseBufferoosContract.Presenter)
            = BrowseViewModelFactory(presenter)
}
