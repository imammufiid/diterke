package com.mufiid.messaging

import org.koin.dsl.module

object MessageModule {
    fun modules() = module {
        single { MessageApi.create() }
    }
}