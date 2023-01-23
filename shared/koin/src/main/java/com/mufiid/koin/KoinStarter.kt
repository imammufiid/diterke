package com.mufiid.koin

import android.content.Context
import com.mufiid.auth.di.FeatureAuthModule
import com.mufiid.core.di.SharedCoreModules
import com.mufiid.locationapi.di.FeatureLocationApiModule
import com.mufiid.messaging.MessageModule
import com.mufiid.network.SharedNetworkModule
import com.mufiid.profile.FeatureCustomerProfileModule
import com.mufiid.searchlocation.di.FeatureCustomerSearchLocationModule
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

@DelicateCoroutinesApi
object KoinStarter {
    fun onCreate(context: Context, featuresModules: List<Module> = emptyList()) {
        val modules = listOf(
            SharedCoreModules.modules(),
            SharedNetworkModule.modules(),
            FeatureLocationApiModule.modules(),
            FeatureAuthModule.modules(),
            FeatureCustomerSearchLocationModule.modules(),
            FeatureCustomerProfileModule.modules(),
            MessageModule.modules()
        ) + featuresModules

        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}