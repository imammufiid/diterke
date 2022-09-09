package com.mufiid.diterke.home

import android.location.Location
import com.mufiid.utils.ActivityListener

interface MainActivityListener : ActivityListener {
    fun onLocationResult(data: Location)
}