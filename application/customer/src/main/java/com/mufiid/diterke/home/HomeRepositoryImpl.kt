package com.mufiid.diterke.home

import android.location.Location
import com.mufiid.core.state.MutableStateEventManager
import com.mufiid.core.state.StateEventManager
import com.mufiid.core.LocationManager
import com.mufiid.core.extensions.mapStateEvent
import com.mufiid.core.state.StateEvent

class HomeRepositoryImpl(
    private val locationManager: LocationManager
) : HomeRepository {
    private val _locationResult = MutableStateEventManager<Location>()
    override val locationResult: StateEventManager<Location>
        get() = _locationResult

    override suspend fun getLocation() {
        _locationResult.emit(StateEvent.Loading())
        locationManager.getLocationFlow()
            .mapStateEvent()
            .collect(_locationResult)
    }
}