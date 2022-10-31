package com.mufiid.home

import android.location.Location
import com.mufiid.core.state.FlowState
import com.mufiid.core.state.StateEventManager
import com.mufiid.locationapi.data.model.entity.LocationData

interface HomeRepository {
    val locationResult: StateEventManager<Location>
    val initialLocation: FlowState<LocationData>
    val locationFrom: FlowState<LocationData>
    val locationDestination: FlowState<LocationData>

    fun setFromLocation(locationData: LocationData)
    fun setDestinationLocation(locationData: LocationData)
    suspend fun reverseCurrentLocation(location: Location)
    suspend fun getLocation()
}