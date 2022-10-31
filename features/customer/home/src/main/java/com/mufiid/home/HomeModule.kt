package com.mufiid.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    fun modules() = module {
        single<HomeRepository> { HomeRepositoryImpl(get(), get()) }

        viewModel { HomeViewModel(get()) }
    }
}