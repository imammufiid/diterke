package com.mufiid.core

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SharedCoreModules  {
    fun modules() = module {
        single { LocationManager(androidContext()) }
    }
}