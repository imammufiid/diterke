package com.mufiid.home

import android.location.Location
import com.google.android.gms.location.LocationServices
import com.mufiid.core.state.MutableStateEventManager
import com.mufiid.core.state.StateEventManager
import com.mufiid.core.LocationManager
import com.mufiid.core.extensions.asFlowStateEvent
import com.mufiid.core.extensions.mapStateEvent
import com.mufiid.core.state.FlowState
import com.mufiid.core.state.StateEvent
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.locationapi.data.model.mapper.Mapper
import com.mufiid.locationapi.data.source.network.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class HomeRepositoryImpl(
    private val locationManager: LocationManager,
    private val locationService: LocationService
) : HomeRepository {
    private val _locationResult = MutableStateEventManager<Location>()
    override val locationResult: StateEventManager<Location>
        get() = _locationResult

    private val _initialLocation: MutableStateFlow<StateEvent<LocationData>> =
        MutableStateFlow(StateEvent.Idle())
    override val initialLocation: FlowState<LocationData>
        get() = _initialLocation

    private val _locationFrom: MutableStateFlow<StateEvent<LocationData>> =
        MutableStateFlow(StateEvent.Idle())
    override val locationFrom: FlowState<LocationData>
        get() = _locationFrom

    private val _locationDestination: MutableStateFlow<StateEvent<LocationData>> =
        MutableStateFlow(StateEvent.Idle())
    override val locationDestination: FlowState<LocationData>
        get() = _locationDestination

    override fun setFromLocation(locationData: LocationData) {
        _locationFrom.value = StateEvent.Success(locationData)
    }

    override fun setDestinationLocation(locationData: LocationData) {
        _locationDestination.value = StateEvent.Success(locationData)
    }

    override suspend fun reverseCurrentLocation(location: Location) {
        val coordinateString = "${location.latitude},${location.longitude}"
        _initialLocation.value = StateEvent.Loading()
        locationService.reverseLocation(coordinateString).asFlowStateEvent {
            println("--------> $it")
            Mapper.mapReverseLocationResponseToData(it)
        }.catch {
            println("-------->F $it")
            emit(StateEvent.Failure(it))
        }.collect {
            _initialLocation.value = it
        }
    }

    override suspend fun getLocation() {
        _locationResult.emit(StateEvent.Loading())
        locationManager.getLocationFlow()
            .mapStateEvent()
            .collect(_locationResult)
    }
}