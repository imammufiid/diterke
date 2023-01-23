package com.mufiid.core.di

import com.mufiid.core.DiterkeBus
import com.mufiid.core.LocationManager
import com.mufiid.core.local.AppPreferences
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@DelicateCoroutinesApi
object SharedCoreModules  {
    fun modules() = module {
        single { LocationManager(androidContext()) }
        single { DiterkeBus() }
        single { AppPreferences(get()) }
    }
}