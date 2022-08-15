package com.mufiid.diterkedriver

import android.app.Application
import com.mufiid.core.KoinStarter

class MainDriver: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinStarter.onCreate(this)
    }
}