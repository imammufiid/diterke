package com.mufiid.diterke.home

import android.location.Location
import com.mufiid.utils.listener.ActivityListener

interface MainActivityListener : ActivityListener {
    fun onLocationResult(data: Location)
}