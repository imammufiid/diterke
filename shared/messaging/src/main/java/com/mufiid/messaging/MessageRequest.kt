package com.mufiid.messaging

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @SerializedName("to")
    val token: String,
    val data: DataRequest
) {

    data class DataRequest(
        val type: String,
        val body: String
    )
}
