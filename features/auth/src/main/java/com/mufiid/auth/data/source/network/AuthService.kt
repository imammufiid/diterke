package com.mufiid.auth.data.source.network

import com.mufiid.auth.data.model.request.CustomerRegisterRequest
import com.mufiid.auth.data.model.request.DriverRegisterRequest
import com.mufiid.auth.data.model.request.LoginRequest
import com.mufiid.auth.data.model.response.BaseResponse
import com.mufiid.auth.data.model.response.LoginResponse
import com.mufiid.network.RetrofitBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(Endpoint.REGISTER_CUSTOMER)
    suspend fun customerRegister(
        @Body params: CustomerRegisterRequest
    ): Response<BaseResponse<Boolean>>

    @POST(Endpoint.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<BaseResponse<LoginResponse>>

    @POST(Endpoint.REGISTER_DRIVER)
    suspend fun driverRegister(
        @Body params: DriverRegisterRequest
    ): Response<BaseResponse<Boolean>>

    object Endpoint {
        private const val PREFIX = "/api/user"
        const val LOGIN = "$PREFIX/login"
        const val REGISTER_CUSTOMER = "$PREFIX/customer/register"
        const val REGISTER_DRIVER = "$PREFIX/customer/driver"
    }

    object QueryName {
    }

    companion object : KoinComponent {
        private val retrofitBuilder: RetrofitBuilder by inject()
        fun build(): AuthService = retrofitBuilder.build().create(AuthService::class.java)
    }
}