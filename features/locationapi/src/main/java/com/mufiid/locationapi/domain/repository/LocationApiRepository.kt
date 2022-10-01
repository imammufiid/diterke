package com.mufiid.locationapi.domain.repository

import com.mufiid.core.state.StateEventManager
import com.mufiid.locationapi.data.model.entity.LocationData

interface LocationApiRepository {
    val searchLocationResult:  StateEventManager<List<LocationData>>
    suspend fun searchLocation(q: String, coordinate: String)
}