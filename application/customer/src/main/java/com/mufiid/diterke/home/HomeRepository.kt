package com.mufiid.diterke.home

import android.location.Location
import com.mufiid.core.state.StateEventManager

interface HomeRepository {
    val locationResult: StateEventManager<Location>
    suspend fun getLocation()
}