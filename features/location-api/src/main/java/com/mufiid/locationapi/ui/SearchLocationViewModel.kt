package com.mufiid.locationapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufiid.core.extensions.asFlowStateEvent
import com.mufiid.core.extensions.convertEventToSubscriber
import com.mufiid.core.state.MutableStateEventManager
import com.mufiid.core.state.StateEvent
import com.mufiid.core.state.StateEventManager
import com.mufiid.core.state.StateEventSubscriber
import com.mufiid.locationapi.LocationService
import com.mufiid.locationapi.Mapper
import com.mufiid.locationapi.entity.LocationData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

typealias StateLocationList = StateEvent<List<LocationData>>

class SearchLocationViewModel(
    private val locationService: LocationService
) : ViewModel() {

    private val _locationManager: MutableStateEventManager<List<LocationData>> =
        MutableStateEventManager()
    private val locationManager: StateEventManager<List<LocationData>>
        get() = _locationManager

    fun searchLocation(q: String) = _locationManager.createScope(viewModelScope).launch {
        val coordinate = "-6.8836002,107.5500096"
        locationService.searchLocation(q, coordinate).asFlowStateEvent {
            Mapper.mapLocationResponseToData(it)
        }.collect(_locationManager)
    }

    fun subscribe(stateEventSubscriber: StateEventSubscriber<List<LocationData>>) {
        convertEventToSubscriber(locationManager, stateEventSubscriber)
    }


}