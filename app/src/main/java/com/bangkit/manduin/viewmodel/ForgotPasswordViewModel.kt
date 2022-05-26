package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.repository.AuthRepository
import com.bangkit.manduin.utils.StateAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _stateAuth = MutableLiveData<StateAuth>()
    val stateAuth: LiveData<StateAuth> = _stateAuth

    fun sendEmailResetPassword(email: String) {
        viewModelScope.launch {
            authRepository.sendEmailResetPassword(email).collect {
                _stateAuth.value = it
            }
        }
    }
}