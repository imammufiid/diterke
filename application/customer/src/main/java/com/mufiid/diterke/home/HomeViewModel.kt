package com.mufiid.diterke.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufiid.core.extensions.convertEventToSubscriber
import com.mufiid.core.state.StateEventSubscriber
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val locationEvent = homeRepository.locationResult

    private val locationScope = locationEvent.createScope(viewModelScope)

    fun subscribe(subscriber: StateEventSubscriber<Location>) {
        convertEventToSubscriber(locationEvent, subscriber)
    }

    fun getLocation() = locationScope.launch {
        homeRepository.getLocation()
    }
}