package com.mufiid.koin

import android.content.Context
import com.mufiid.auth.di.AuthModule
import com.mufiid.core.CoreModules
import com.mufiid.locationapi.LocationApiModule
import com.mufiid.network.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinStarter {
    fun onCreate(context: Context, featuresModules: List<Module> = emptyList()) {
        val modules = listOf(
            CoreModules.modules(),
            NetworkModule.modules(),
            LocationApiModule.modules(),
            AuthModule.modules()
        ) + featuresModules

        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}