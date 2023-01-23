package com.mufiid.diterke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mufiid.profile.data.ProfileRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val profileSafeScope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    val userState = profileRepository.userState.asLiveData(profileSafeScope.coroutineContext)

    fun getCurrentUser() = profileSafeScope.launch {
        profileRepository.getUser()
    }

}