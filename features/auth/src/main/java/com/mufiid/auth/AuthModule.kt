package com.mufiid.auth

import org.koin.dsl.module

object AuthModule {
    fun modules() = module {
        single { AuthServiceProvider.provideAuthService() }
    }
}