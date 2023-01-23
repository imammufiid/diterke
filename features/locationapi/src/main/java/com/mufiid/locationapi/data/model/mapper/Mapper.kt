package com.mufiid.locationapi.data.model.mapper

import com.google.android.gms.maps.model.LatLng
import com.mufiid.utils.orNol
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.locationapi.data.model.response.LocationResponse
import com.mufiid.locationapi.data.model.response.ReverseLocationResponse

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

    fun mapReverseLocationResponseToData(reverseLocationResponse: ReverseLocationResponse?): LocationData {
        val data = reverseLocationResponse?.data ?: throw Throwable("Error response data")
        val name = data.name.orEmpty()
        val address = "${data.address?.distric}, ${data.address?.country}, ${data.address?.city}"
        val latLng = LatLng(data.coordinate?.latitude.orNol(), data.coordinate?.longitude.orNol())
        return LocationData(name, address, latLng)
    }
}