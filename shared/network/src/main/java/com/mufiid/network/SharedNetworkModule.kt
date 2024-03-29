package com.mufiid.network

import org.koin.dsl.module

object SharedNetworkModule {
    fun modules() = module {
        factory { Tokenizer() }
        factory { RetrofitBuilder(get()) }
    }
}