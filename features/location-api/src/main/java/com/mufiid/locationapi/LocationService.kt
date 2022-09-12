package com.mufiid.locationapi

import com.mufiid.locationapi.response.LocationResponse
import com.mufiid.network.RetrofitBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET(Endpoint.SEARCH)
    suspend fun searchLocation(
        @Query("name") name: String,
        @Query("coordinate") coordinate: String
    ): Response<LocationResponse>

    object Endpoint {
        internal const val SEARCH = "/api/location/search"
    }

    object QueryName {
        internal const val SEARCH_NAME = "name"
        internal const val SEARCH_COORDINATE = "coordinate"
    }

    companion object : KoinComponent {
        private val retrofitBuilder: RetrofitBuilder by inject()
        fun build() = retrofitBuilder.build().create(LocationService::class.java)
    }
}