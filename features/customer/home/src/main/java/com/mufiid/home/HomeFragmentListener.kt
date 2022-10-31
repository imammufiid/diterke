package com.mufiid.home

import android.location.Location
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.utils.listener.FragmentListener

interface HomeFragmentListener : FragmentListener {
    fun onMessageFromActivity(message: String)
    fun requestLocation()
    fun requestInitialData(location: Location)
    fun onDataLocation(from: LocationData, destination: LocationData)
    fun pushLoadingFormLocation()
}