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
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    fun getSession() = userPreferences.getSession().asLiveData()

    fun deleteSession() {
        viewModelScope.launch {
            userPreferences.deleteSession()
        }
    }
}