package com.mufiid.searchlocation

import com.mufiid.searchlocation.data.repository.LocationApiRepositoryImpl
import com.mufiid.searchlocation.domain.repository.LocationApiRepository
import com.mufiid.searchlocation.ui.SearchLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LocationApiModule {
    fun modules() = module {
        single { LocationService.build() }
        single<LocationApiRepository> { LocationApiRepositoryImpl(get()) }
        viewModel { SearchLocationViewModel(get()) }
    }
}