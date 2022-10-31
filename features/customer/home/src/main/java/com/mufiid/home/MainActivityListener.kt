package com.mufiid.home

import android.location.Location
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.utils.listener.ActivityListener

interface MainActivityListener : ActivityListener {
    fun onLocationResult(data: Location)
    fun sendFromLocation(from: LocationData)
    fun sendDestinationLocation(destination: LocationData)
    fun navigateToSearchLocation(formType: Int)

    object FormType {
        const val FROM = 1
        const val DEST = 2
    }
}