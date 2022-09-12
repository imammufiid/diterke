package com.mufiid.locationapi

import com.mufiid.locationapi.ui.SearchLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LocationApiModule {
    fun modules() = module {
        single { LocationService.build() }
        viewModel { SearchLocationViewModel(get()) }
    }
}