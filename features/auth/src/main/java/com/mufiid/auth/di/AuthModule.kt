package com.mufiid.auth.di

import com.mufiid.auth.domain.repository.AuthRepository
import com.mufiid.auth.data.repository.AuthRepositoryImpl
import com.mufiid.auth.ui.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthModule {
    fun modules() = module {
        single { AuthServiceProvider.provideAuthService() }
        single<AuthRepository> { AuthRepositoryImpl(get()) }
        viewModel { AuthViewModel(get()) }
    }
}