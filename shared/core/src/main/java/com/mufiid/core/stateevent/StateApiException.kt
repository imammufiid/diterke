package com.mufiid.core.stateevent

class StateApiException(message: String, private val code: Int): Throwable(message) {
    fun code() = code
}