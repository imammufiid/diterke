package com.mufiid.auth.data.model.response


import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    var data: T?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
)