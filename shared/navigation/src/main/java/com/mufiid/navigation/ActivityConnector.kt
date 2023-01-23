package com.mufiid.navigation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ActivityConnector : KoinComponent {
    val customerActivity: CustomerActivityConnector by inject()
}