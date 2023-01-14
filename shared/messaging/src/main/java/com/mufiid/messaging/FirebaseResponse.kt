package com.mufiid.messaging

import com.google.gson.annotations.SerializedName

data class FirebaseResponse(
    @SerializedName("results")
    var results: List<FirebaseResult>? = emptyList()
) {

    data class FirebaseResult(
        @SerializedName("message_id")
        var messageId: String? = null,
        @SerializedName("error")
        var error: String? = null
    )
}
