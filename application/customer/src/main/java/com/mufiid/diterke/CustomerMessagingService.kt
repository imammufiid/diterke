package com.mufiid.diterke

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomerMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        // ChatServiceBind.bind(data)

        println("CUSTOMER --> $data")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}