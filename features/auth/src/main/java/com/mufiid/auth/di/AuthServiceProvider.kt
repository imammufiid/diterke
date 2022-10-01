package com.mufiid.auth.di

import com.mufiid.auth.data.source.network.AuthService

object AuthServiceProvider {
    fun provideAuthService(): AuthService {
        return AuthService.build()
    }
}