package com.mufiid.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufiid.auth.domain.repository.AuthRepository
import com.mufiid.auth.data.model.request.CustomerRegisterRequest
import com.mufiid.auth.data.model.request.DriverRegisterRequest
import com.mufiid.auth.data.model.request.LoginRequest
import com.mufiid.core.extensions.convertEventToSubscriber
import com.mufiid.core.stateevent.StateEventSubscriber
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    /**
     * Customer Register
     */
    private val customerRegisterEvent = authRepository.registerCustomerState
    fun subscribeCustomerRegisterEvent(subscriber: StateEventSubscriber<Boolean>) {
        convertEventToSubscriber(customerRegisterEvent, subscriber)
    }
    fun registerCustomer(registerCustomerParams: CustomerRegisterRequest) =
        customerRegisterEvent.createScope(viewModelScope).launch {
            authRepository.registerCustomer(registerCustomerParams)
        }

    /**
     * Driver Register
     */
    private val driverRegisterEvent = authRepository.registerDriverState
    fun subscribeDriverRegisterEvent(subscriber: StateEventSubscriber<Boolean>) {
        convertEventToSubscriber(driverRegisterEvent, subscriber)
    }
    fun registerDriver(params: DriverRegisterRequest) =
        customerRegisterEvent.createScope(viewModelScope).launch {
            authRepository.registerDriver(params)
        }

    /**
     * Login
     */
    private val loginEvent = authRepository.loginState
    fun subscribeLoginEvent(subscriber: StateEventSubscriber<String>) {
        convertEventToSubscriber(loginEvent, subscriber)
    }
    fun login(params: LoginRequest) =
        customerRegisterEvent.createScope(viewModelScope).launch {
            authRepository.login(params)
        }
}