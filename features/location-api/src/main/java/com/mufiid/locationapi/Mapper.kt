package com.mufiid.locationapi

import com.google.android.gms.maps.model.LatLng
import com.mufiid.core.extensions.orNol
import com.mufiid.locationapi.entity.LocationData
import com.mufiid.locationapi.response.LocationResponse

object Mapper {
    fun mapLocationResponseToData(locationResponse: LocationResponse?): List<LocationData> {
        val mapper: (LocationResponse.Data?) -> LocationData = {
            val name = it?.name.orEmpty()
            val addressName =
                "${it?.address?.district}, ${it?.address?.country}, ${it?.address?.city}"
            val latLng = LatLng(it?.coordinate?.latitude.orNol(), it?.coordinate?.longitude.orNol())
            LocationData(name, addressName, latLng)
        }
        return locationResponse?.data?.map(mapper).orEmpty()
    }
}