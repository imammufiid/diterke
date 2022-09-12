package com.mufiid.core.state

class StateApiException(message: String, private val code: Int): Throwable(message) {
    fun code() = code
}