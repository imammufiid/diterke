package com.mufiid.profile.data

import com.mufiid.core.stateevent.StateEvent
import com.mufiid.profile.data.model.User
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val userState: StateFlow<StateEvent<User>>
    suspend fun getUser()
}