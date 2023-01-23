package com.mufiid.profile.ui.navigation

import androidx.fragment.app.Fragment
import com.mufiid.navigation.ProfileFragmentConnector
import com.mufiid.profile.ui.ProfileFragment
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ProfileFragmentConnectorProvider: ProfileFragmentConnector {
    override val profileFragment: KClass<Fragment>
        get() = ProfileFragment::class as KClass<Fragment>
}