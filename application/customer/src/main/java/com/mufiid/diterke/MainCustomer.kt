package com.mufiid.diterke

import android.app.Application
import com.mufiid.core.KoinStarter

class MainCustomer: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinStarter.onCreate(this)
    }
}