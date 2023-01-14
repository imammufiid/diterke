package com.mufiid.auth.domain.repository

import com.mufiid.auth.data.model.request.CustomerRegisterRequest
import com.mufiid.auth.data.model.request.DriverRegisterRequest
import com.mufiid.auth.data.model.request.LoginRequest
import com.mufiid.core.stateevent.StateEventManager

interface AuthRepository {
    val registerCustomerState: StateEventManager<Boolean>
    suspend fun registerCustomer(params: CustomerRegisterRequest)

    val registerDriverState: StateEventManager<Boolean>
    suspend fun registerDriver(params: DriverRegisterRequest)

    val loginState: StateEventManager<String>
    suspend fun login(params: LoginRequest)
}