package com.mufiid.diterkedriver

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.mufiid.koin.KoinStarter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainDriver: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinStarter.onCreate(this)

        GlobalScope.launch {
            getToken {
                println("DRIVER -- TOKEN -- \n$it\n")
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
                println("DRIVER -- task -> $task")
            }
            .addOnSuccessListener {
                token.invoke(it)
            }
            .addOnFailureListener {
                println("DRIVER -- ${it.message}")
                it.printStackTrace()
            }
    }
}