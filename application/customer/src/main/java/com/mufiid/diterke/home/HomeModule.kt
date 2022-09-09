package com.mufiid.diterke.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    fun modules() = module {
        single<HomeRepository> { HomeRepositoryImpl(get()) }

        viewModel { HomeViewModel(get()) }
    }
}