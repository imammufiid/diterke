package com.mufiid.searchlocation.domain.repository

import com.mufiid.core.state.StateEventManager
import com.mufiid.searchlocation.data.model.entity.LocationData

interface LocationApiRepository {
    val searchLocationResult:  StateEventManager<List<LocationData>>
    suspend fun searchLocation(q: String, coordinate: String)
}