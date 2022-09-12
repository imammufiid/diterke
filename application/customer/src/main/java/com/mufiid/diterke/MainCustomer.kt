package com.mufiid.diterke

import android.app.Application
import com.mufiid.koin.KoinStarter
import com.mufiid.diterke.home.HomeModule

class MainCustomer : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinStarter.onCreate(
            this, listOf(
                HomeModule.modules()
            )
        )
    }
}