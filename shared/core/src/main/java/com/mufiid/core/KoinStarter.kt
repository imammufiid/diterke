package com.mufiid.core

import android.content.Context
import com.mufiid.auth.AuthModule
import com.mufiid.network.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinStarter {
    fun onCreate(context: Context) {
        val modules = listOf(
            NetworkModule.modules(),
            AuthModule.modules()
        )

        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}