package com.mufiid.auth

import com.mufiid.network.RetrofitBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(Endpoint.LOGIN)
    suspend fun login(
        @Body request: Any
    ): Response<Any>

    @POST(Endpoint.REGISTER_DRIVER)
    suspend fun registerDriver(
        @Body request: Any
    ): Response<Any>

    @POST(Endpoint.REGISTER_CUSTOMER)
    suspend fun registerCustomer(
        @Body request: Any
    ): Response<Any>

    object Endpoint {
        private const val PREFIX = "/api/user"
        const val LOGIN = "$PREFIX/login"
        const val REGISTER_CUSTOMER = "$PREFIX/customer/register"
        const val REGISTER_DRIVER = "$PREFIX/customer/driver"
    }

    companion object : KoinComponent {
        private val retrofitBuilder : RetrofitBuilder by inject()

        fun build() = retrofitBuilder.build().create(AuthService::class.java)
    }
}