package com.mufiid.profile.data

import com.mufiid.network.RetrofitBuilder
import com.mufiid.profile.data.model.UserResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.create
import retrofit2.http.GET

interface ProfileService {
    @GET("/api/user")
    suspend fun getUser(): Response<UserResponse>

    companion object : KoinComponent {
        private val retrofitBuilder: RetrofitBuilder by inject()
        fun build(): ProfileService {
            return retrofitBuilder.build(true).create()
        }
    }
}