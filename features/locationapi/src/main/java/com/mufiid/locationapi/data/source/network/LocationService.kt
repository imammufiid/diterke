package com.mufiid.locationapi.data.source.network

import com.mufiid.locationapi.data.model.response.LocationResponse
import com.mufiid.locationapi.data.model.response.ReverseLocationResponse
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

    @GET(Endpoint.REVERSE)
    suspend fun reverseLocation(
        @Query(QueryName.SEARCH_COORDINATE) coordinate: String
    ): Response<ReverseLocationResponse>

    object Endpoint {
        internal const val SEARCH = "/api/location/search"
        internal const val REVERSE = "/api/location/reverse"
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