package com.mufiid.auth.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mufiid.auth.databinding.ActivityAuthBinding
import com.mufiid.auth.data.model.request.CustomerRegisterRequest
import com.mufiid.auth.data.model.request.DriverRegisterRequest
import com.mufiid.auth.data.model.request.LoginRequest
import com.mufiid.core.stateevent.StateEventSubscriber
import com.mufiid.core.view.base.BindingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : BindingActivity<ActivityAuthBinding>() {
    private val viewModel: AuthViewModel by viewModel()

    companion object {
        fun launch(context: Context, userRole: Int = 0) {
            context.startActivity(Intent(context, AuthActivity::class.java).apply {
                putExtra("role_extra", userRole)
            })
        }
    }

    private val role: Int by lazy {
        intent.getIntExtra("role_extra", 0)
    }

    override fun inflateBinding(): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(layoutInflater)
    }

    private val paramsCustomer
        get() = CustomerRegisterRequest(
            username = binding.formAuth.userNameEditText,
            password = binding.formAuth.secondaryEditText,
            firstName = "",
            lastName = "",
            email = ""
        )

    private val paramsDriver
        get() = DriverRegisterRequest(
            username = binding.formAuth.userNameEditText,
            password = binding.formAuth.secondaryEditText,
            firstName = "",
            lastName = "",
            email = "",
            vehicleNumber = ""
        )

    override fun onCreateBinding(savedInstanceState: Bundle?) {

        binding.formAuth.setOnButtonClickListener {
            if (!binding.formAuth.isSignIn) {
                if (role == 0) {
                    viewModel.registerCustomer(paramsCustomer)
                } else {
                    viewModel.registerDriver(paramsDriver)
                }
            } else {
                val loginRequest = LoginRequest(
                    username = binding.formAuth.userNameEditText,
                    password = binding.formAuth.secondaryEditText,
                )
                viewModel.login(loginRequest)
            }
        }

        viewModel.subscribeLoginEvent(subscriberLogin)
        viewModel.subscribeDriverRegisterEvent(subscriberDriverRegister)
        viewModel.subscribeCustomerRegisterEvent(subscriberCustomerRegister)
    }

    private val subscriberLogin = object : StateEventSubscriber<String> {
        override fun onIdle() {
            println("EVENT_LOGIN => OnIdle")
        }

        override fun onLoading() {
            println("EVENT_LOGIN => onLoading")
        }

        override fun onFailure(throwable: Throwable) {
            println("EVENT_LOGIN => onFailure ${throwable.message}")
        }

        override fun onSuccess(data: String) {
            println("EVENT_LOGIN => onSuccess $data")
        }
    }

    private val subscriberDriverRegister = object : StateEventSubscriber<Boolean> {
        override fun onIdle() {
            println("EVENT_REGISTER_DRIVER => OnIdle")
        }

        override fun onLoading() {
            println("EVENT_REGISTER_DRIVER => onLoading")
        }

        override fun onFailure(throwable: Throwable) {
            println("EVENT_REGISTER_DRIVER => onFailure ${throwable.message}")
        }

        override fun onSuccess(data: Boolean) {
            println("EVENT_REGISTER_DRIVER => onSuccess $data")
        }

    }

    private val subscriberCustomerRegister = object : StateEventSubscriber<Boolean> {
        override fun onIdle() {
            println("EVENT_REGISTER_CUSTOMER => OnIdle")
        }

        override fun onLoading() {
            println("EVENT_REGISTER_CUSTOMER => onLoading")
        }

        override fun onFailure(throwable: Throwable) {
            println("EVENT_REGISTER_CUSTOMER => onFailure ${throwable.message}")
        }

        override fun onSuccess(data: Boolean) {
            println("EVENT_REGISTER_CUSTOMER => onSuccess $data")
        }

    }

}