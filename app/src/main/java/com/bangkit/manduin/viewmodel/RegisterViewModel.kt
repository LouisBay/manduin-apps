package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.repository.AuthRepository
import com.bangkit.manduin.data.repository.UserPreferences
import com.bangkit.manduin.model.UserSessionModel
import com.bangkit.manduin.utils.StateAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _stateAuth = MutableLiveData<StateAuth>()
    val stateAuth: LiveData<StateAuth> = _stateAuth

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            authRepository.loginWithGoogle(idToken).collect {
                _stateAuth.value = it
            }
        }
    }

    fun registerUser(fullname: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.registerUser(fullname, email, password).collect {
                _stateAuth.value = it
            }
        }
    }

    fun saveSession(session: UserSessionModel) {
        viewModelScope.launch {
            userPreferences.saveSession(session)
        }
    }
}