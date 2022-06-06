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
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun getOpennedState() = userPreferences.getOpennedState().asLiveData()

    fun setOpennedStateToTrue() {
        viewModelScope.launch {
            userPreferences.setOpennedStateToTrue()
        }
    }
}