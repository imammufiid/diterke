package com.mufiid.searchlocation.data.repository

import com.mufiid.core.extensions.asFlowStateEvent
import com.mufiid.core.state.MutableStateEventManager
import com.mufiid.core.state.StateEvent
import com.mufiid.core.state.StateEventManager
import com.mufiid.searchlocation.LocationService
import com.mufiid.searchlocation.Mapper
import com.mufiid.searchlocation.data.model.entity.LocationData
import com.mufiid.searchlocation.domain.repository.LocationApiRepository

class LocationApiRepositoryImpl(
    private val locationService: LocationService
) : LocationApiRepository {
    private val _searchLocationResult = MutableStateEventManager<List<LocationData>>()
    override val searchLocationResult: StateEventManager<List<LocationData>>
        get() = _searchLocationResult

    override suspend fun searchLocation(q: String, coordinate: String) {
        _searchLocationResult.emit(StateEvent.Loading())
        locationService.searchLocation(q, coordinate).asFlowStateEvent {
            Mapper.mapLocationResponseToData(it)
        }.collect(_searchLocationResult)
    }
}