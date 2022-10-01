package com.mufiid.profile

import com.mufiid.navigation.ProfileFragmentConnector
import org.koin.dsl.module

object FeatureCustomerProfileModule {
    fun modules() = module {
        single<ProfileFragmentConnector> { ProfileFragmentConnectorProvider() }
    }
}