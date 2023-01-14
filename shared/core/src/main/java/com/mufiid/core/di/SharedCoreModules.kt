package com.mufiid.core.di

import com.mufiid.core.DiterkeBus
import com.mufiid.core.LocationManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SharedCoreModules  {
    fun modules() = module {
        single { LocationManager(androidContext()) }
        single { DiterkeBus() }
    }
}