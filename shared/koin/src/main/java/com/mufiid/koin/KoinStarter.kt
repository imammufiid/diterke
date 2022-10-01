package com.mufiid.koin

import android.content.Context
import com.mufiid.auth.di.FeatureAuthModule
import com.mufiid.core.SharedCoreModules
import com.mufiid.locationapi.di.FeatureLocationApiModule
import com.mufiid.network.SharedNetworkModule
import com.mufiid.profile.FeatureCustomerProfileModule
import com.mufiid.searchlocation.di.FeatureCustomerSearchLocationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinStarter {
    fun onCreate(context: Context, featuresModules: List<Module> = emptyList()) {
        val modules = listOf(
            SharedCoreModules.modules(),
            SharedNetworkModule.modules(),
            FeatureLocationApiModule.modules(),
            FeatureAuthModule.modules(),
            FeatureCustomerSearchLocationModule.modules(),
            FeatureCustomerProfileModule.modules()
        ) + featuresModules

        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}