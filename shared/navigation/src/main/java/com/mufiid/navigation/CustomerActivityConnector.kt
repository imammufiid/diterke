package com.mufiid.navigation

import android.content.Context

interface CustomerActivityConnector {
    fun toMainActivity(context: Context?)
    fun toAuthActivity(context: Context?)
}