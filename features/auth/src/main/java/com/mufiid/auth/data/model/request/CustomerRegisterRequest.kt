package com.mufiid.auth.data.model.request

import com.google.gson.annotations.SerializedName

data class CustomerRegisterRequest(
    @field:SerializedName("username")
    var username: String = "",

    @field:SerializedName("password")
    var password: String = "",

    @field:SerializedName("first_name")
    var firstName: String = "",

    @field:SerializedName("last_name")
    var lastName: String = "",

    @field:SerializedName("email")
    var email: String = "",

    @field:SerializedName("phone_number")
    var phoneNumber: String = "081",
)
