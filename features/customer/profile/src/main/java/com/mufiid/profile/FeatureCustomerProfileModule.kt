package com.mufiid.profile

import com.mufiid.navigation.ProfileFragmentConnector
import com.mufiid.profile.data.ProfileRepositoryImpl
import com.mufiid.profile.data.ProfileService
import com.mufiid.profile.ui.navigation.ProfileFragmentConnectorProvider
import org.koin.dsl.module

object FeatureCustomerProfileModule {
    fun modules() = module {
        single<ProfileFragmentConnector> { ProfileFragmentConnectorProvider() }
        single { ProfileService.build() }
        factory { ProfileRepositoryImpl.build(get()) }
    }
}