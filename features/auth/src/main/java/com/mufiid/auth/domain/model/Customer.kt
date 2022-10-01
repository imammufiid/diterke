package com.mufiid.auth.domain.model

data class Customer(
    var expiredAt: Long?,
    var id: String?,
    var password: String?,
    var role: String?,
    var updatedAt: Long?,
    var username: String?
)
