package com.bangkit.manduin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.manduin.data.repository.AuthRepository
import com.bangkit.manduin.data.repository.UserPreferences
import com.bangkit.manduin.model.UserSessionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    fun loginWithGoogle(idToken: String) = authRepository.loginWithGoogle(idToken)

    fun saveSession(session: UserSessionModel) {
        viewModelScope.launch {
            userPreferences.saveSession(session)
        }
    }
}