package com.mufiid.searchlocation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufiid.core.extensions.convertEventToSubscriber
import com.mufiid.core.state.StateEventSubscriber
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.locationapi.domain.repository.LocationApiRepository
import kotlinx.coroutines.launch

class SearchLocationViewModel(
    private val locationApiRepository: LocationApiRepository
) : ViewModel() {

    private val locationEvent = locationApiRepository.searchLocationResult
    fun subscribe(subscriber: StateEventSubscriber<List<LocationData>>) {
        convertEventToSubscriber(locationEvent, subscriber)
    }

    fun searchLocation(q: String) = locationEvent.createScope(viewModelScope).launch {
        val coordinate = "-6.8836002,107.5500096"
        locationApiRepository.searchLocation(q, coordinate)
    }

}