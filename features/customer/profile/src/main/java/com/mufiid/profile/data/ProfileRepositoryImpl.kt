package com.mufiid.profile.data

import com.mufiid.core.RepositoryProvider
import com.mufiid.core.stateevent.StateEvent
import com.mufiid.profile.data.model.User
import com.mufiid.profile.data.model.mapper.ProfileMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileRepositoryImpl(
    private val profileService: ProfileService
) : ProfileRepository, RepositoryProvider() {
    private val _userState: MutableStateFlow<StateEvent<User>> = MutableStateFlow(StateEvent.Idle())
    override val userState: StateFlow<StateEvent<User>>
        get() = _userState

    override suspend fun getUser() {
        bindToState(
            stateFlow = _userState,
            onFetch = { profileService.getUser() },
            mapper = { ProfileMapper.mapResponseToUser(it) }
        )
    }

    companion object {
        fun build(profileService: ProfileService): ProfileRepository {
            return ProfileRepositoryImpl(profileService)
        }
    }
}