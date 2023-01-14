package com.mufiid.auth.data.repository

import com.mufiid.auth.data.source.network.AuthService
import com.mufiid.auth.data.model.request.CustomerRegisterRequest
import com.mufiid.auth.data.model.request.DriverRegisterRequest
import com.mufiid.auth.data.model.request.LoginRequest
import com.mufiid.auth.domain.repository.AuthRepository
import com.mufiid.core.extensions.asFlowStateEvent
import com.mufiid.core.stateevent.MutableStateEventManager
import com.mufiid.core.stateevent.StateEventManager

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    private val _registerCustomerState = MutableStateEventManager<Boolean>()
    override val registerCustomerState: StateEventManager<Boolean>
        get() = _registerCustomerState

    override suspend fun registerCustomer(params: CustomerRegisterRequest) {
        authService.customerRegister(params).asFlowStateEvent {
            it.data ?: false
        }.collect(_registerCustomerState)
    }

    private val _registerDriverState = MutableStateEventManager<Boolean>()
    override val registerDriverState: StateEventManager<Boolean>
        get() = _registerDriverState

    override suspend fun registerDriver(params: DriverRegisterRequest) {
        authService.driverRegister(params).asFlowStateEvent {
            it.data ?: false
        }.collect(_registerDriverState)
    }

    private val _loginState = MutableStateEventManager<String>()
    override val loginState: StateEventManager<String>
        get() = _loginState

    override suspend fun login(params: LoginRequest) {
        authService.login(params).asFlowStateEvent {
            "${it.data}"
        }.collect(_loginState)
    }
}