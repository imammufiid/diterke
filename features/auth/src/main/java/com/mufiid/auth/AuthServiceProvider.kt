package com.mufiid.auth

object AuthServiceProvider {
    fun provideAuthService(): AuthService {
        return AuthService.build()
    }
}