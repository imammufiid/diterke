package com.mufiid.diterke

import android.content.Context
import com.mufiid.auth.ui.AuthActivity
import com.mufiid.navigation.CustomerActivityConnector
import com.mufiid.navigation.intentTo

class ActivityNavigatorProvider: CustomerActivityConnector {
    override fun toMainActivity(context: Context?) {
        context?.intentTo(MainActivity::class)
    }

    override fun toAuthActivity(context: Context?) {
        context?.intentTo(AuthActivity::class)
    }

    companion object {
        fun build(): ActivityNavigatorProvider {
            return ActivityNavigatorProvider()
        }
    }
}