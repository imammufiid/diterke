package com.mufiid.core

import android.content.Context
//import com.mufiid.auth.AuthModule
import com.mufiid.network.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinStarter {
    fun onCreate(context: Context, featuresModules: List<Module> = emptyList()) {
        val modules = listOf(
            CoreModules.modules(),
            NetworkModule.modules()
        ) + featuresModules

        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}