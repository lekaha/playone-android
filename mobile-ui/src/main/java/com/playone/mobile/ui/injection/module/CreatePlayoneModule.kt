package com.playone.mobile.ui.injection.module

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_NONE
import com.google.android.gms.location.places.GeoDataClient
import com.playone.mobile.domain.interactor.playone.CreatePlayone
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.createPlayone.CreatePlayoneContract
import com.playone.mobile.presentation.createPlayone.CreatePlayonePresenter
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.R
import com.playone.mobile.ui.create.GooglePlacesAutocompleteAdapter
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
import com.playone.mobile.ui.model.CreatePlayoneViewModel
import dagger.Module
import dagger.Provides
import java.util.Locale

@Module
class CreatePlayoneModule {

    @Provides
    @JvmSuppressWildcards
    internal fun provideCreatePlayonePresenter(
        createPlayone: CreatePlayone,
        viewMapper: Mapper<PlayoneView, Playone>
    ): CreatePlayoneContract.Presenter =
        CreatePlayonePresenter(createPlayone, viewMapper)

    @Provides
    internal fun provideCreatePlayoneViewModelFactory(
        presenter: CreatePlayoneContract.Presenter,
        fusedLocationProviderClient: FusedLocationProviderClient,
        geoDataClient: GeoDataClient,
        geocoder: Geocoder,
        autocompleteFilter: AutocompleteFilter
    ) = CreatePlayoneViewModel.CreatePlayoneViewModelFactory(presenter,
                                                             fusedLocationProviderClient,
                                                             geoDataClient,
                                                             geocoder,
                                                             autocompleteFilter)

    @Provides
    internal fun provideGooglePlacesAutocompleteAdapter(
        geoDataClient: GeoDataClient,
        autocompleteFilter: AutocompleteFilter,
        @ApplicationContext context: Context
    ) = GooglePlacesAutocompleteAdapter(geoDataClient,
                                        autocompleteFilter,
                                        context,
                                        R.layout.row_auto_complete_address)

    @Provides
    internal fun provideAutocompleteFilter() =
        AutocompleteFilter.Builder()
            .setCountry(Locale.JAPAN.country)
            .setTypeFilter(TYPE_FILTER_NONE)
            .build()
}