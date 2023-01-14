package com.mufiid.core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DiterkeBus {
    private val dataFlow: MutableStateFlow<DataBus<*>?> = MutableStateFlow(null)

    fun <T> emit(key: String, data: T) {
        val dataBus = DataBus(key, data, System.currentTimeMillis())
        dataFlow.value = dataBus
    }

    fun <T : Any> subscribeAsync(key: String, data: (T) -> Unit) {
        GlobalScope.launch {
            dataFlow.asSharedFlow()
                .filterNotNull()
                .filter { it.key == key }
                .collect {
                    data.invoke(it.data as T)
                }
        }
    }

    suspend fun <T : Any> subscribe(key: String, data: (T) -> Unit) {
        dataFlow.asSharedFlow()
            .filterNotNull()
            .filter { it.key == key }
            .collect {
                data.invoke(it.data as T)
            }
    }

    data class DataBus<T>(
        val key: String,
        val data: T,
        val time: Long
    )

    companion object : KoinComponent {

        fun instance(): DiterkeBus {
            return get()
        }
    }
}