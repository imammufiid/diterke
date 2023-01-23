package com.mufiid.core.extensions

import android.util.MalformedJsonException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import com.mufiid.core.stateevent.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

val <T>StateEvent<T>.value: T?
    get() {
        return if (this is StateEvent.Success) {
            this.data
        } else {
            null
        }
    }

fun <T> ViewModel.convertEventToSubscriber(
    eventManager: StateEventManager<T>,
    subscriber: StateEventSubscriber<T>
) {
    eventManager.subscribe(
        scope = viewModelScope,
        onIdle = subscriber::onIdle,
        onLoading = subscriber::onLoading,
        onFailure = subscriber::onFailure,
        onSuccess = subscriber::onSuccess
    )
}

fun <T> Flow<T>.mapStateEvent(): FlowState<T> {
    return this.catch {
        StateEvent.Failure<T>(it)
    }.map {
        StateEvent.Success(it)
    }
}

fun <T, U> Response<T>.asFlowStateEvent(mapper: (T) -> U): FlowState<U> {
    return flow {
        emit(StateEvent.Loading())
        delay(2000)
        val emitData = try {
            val body = body()
            if (isSuccessful && body != null) {
                val data = mapper.invoke(body)
                StateEvent.Success(data)
            } else {
                val throwable = StateApiException(message(), code())
                println(throwable)
                StateEvent.Failure(throwable)
            }
        } catch (e: Throwable) {
            println("--------------> ${e.message}")
            StateEvent.Failure(e)
        }
        emit(emitData)
    }
}

fun <T, U> Response<T>.reducer(mapper: (T) -> U): StateEvent<U> {
    return try {
        val body = body()
        if (isSuccessful && body != null) {
            val data = mapper.invoke(body)
            StateEvent.Success(data)
        } else {
            val throwable = StateApiException(message(), code())
            StateEvent.Failure(throwable)
        }
    } catch (e: Throwable) {
        StateEvent.Failure(e)
    } catch (e: MalformedJsonException) {
        StateEvent.Failure(e)
    } catch (e: JsonSyntaxException) {
        StateEvent.Failure(e)
    }
}

fun <T> StateEvent<T>.onSuccess(action: T.() -> Unit) {
    if (this is StateEvent.Success) {
        action.invoke(data)
    }
}

fun <T> StateEvent<T>.onLoading(action: () -> Unit) {
    if (this is StateEvent.Loading) {
        action.invoke()
    }
}

fun <T> StateEvent<T>.onFailure(action: Throwable.() -> Unit) {
    if (this is StateEvent.Failure) {
        action.invoke(exception)
    }
}