package com.mufiid.diterke

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainCustomerModule {
    fun modules() = module {
        single { ActivityNavigatorProvider.build() }
        viewModel { MainViewModel(get()) }
    }
}