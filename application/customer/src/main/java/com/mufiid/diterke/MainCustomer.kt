package com.mufiid.diterke

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.mufiid.koin.KoinStarter
import com.mufiid.home.HomeModule
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainCustomer : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinStarter.onCreate(
            this, listOf(
                HomeModule.modules()
            )
        )

        GlobalScope.launch {
            getToken {
                println("CUSTOMER -- TOKEN -- \n$it\n")
            }
        }
    }

    private fun getToken(token: (String) -> Unit) {
        Firebase.messaging.token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    token.invoke(result)
                }
                println("CUSTOMER -- task -> $task")
            }
            .addOnSuccessListener {
                token.invoke(it)
            }
            .addOnFailureListener {
                println("CUSTOMER -- ${it.message}")
                it.printStackTrace()
            }
    }
}