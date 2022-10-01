package com.mufiid.locationapi.data.repository

import com.mufiid.core.extensions.asFlowStateEvent
import com.mufiid.core.state.MutableStateEventManager
import com.mufiid.core.state.StateEvent
import com.mufiid.core.state.StateEventManager
import com.mufiid.locationapi.data.source.network.LocationService
import com.mufiid.locationapi.data.model.mapper.Mapper
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.locationapi.domain.repository.LocationApiRepository

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