package com.mufiid.profile.data.model.mapper

import com.mufiid.profile.data.model.User
import com.mufiid.profile.data.model.UserResponse

object ProfileMapper {
    fun mapResponseToUser(response: UserResponse?): User {
        return User(
            id = response?.data?.id.orEmpty(),
            username = response?.data?.username.orEmpty(),
            role = response?.data?.role.orEmpty(),
            fcmToken = response?.data?.fcmToken.orEmpty()
        )
    }
}