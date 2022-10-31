package com.mufiid.diterkedriver

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DriverMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        // ChatServiceBind.bind(data)

        println("DRIVER --> $data")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}