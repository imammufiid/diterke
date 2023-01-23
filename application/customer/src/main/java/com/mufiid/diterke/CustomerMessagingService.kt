package com.mufiid.diterke

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mufiid.core.DiterkeBus
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class CustomerMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        // ChatServiceBind.bind(data)

        println("CUSTOMER --> $data")
        DiterkeBus.instance().emit("notification", data.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}