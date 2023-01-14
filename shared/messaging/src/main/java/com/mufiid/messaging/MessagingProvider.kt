package com.mufiid.messaging

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal object MessagingProvider : KoinComponent {
    val api: MessageApi by inject()
}