package com.mufiid.searchlocation.di

import com.mufiid.searchlocation.ui.SearchLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureCustomerSearchLocationModule {
    fun modules() = module {
        viewModel { SearchLocationViewModel(get()) }
    }
}