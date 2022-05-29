package com.bangkit.manduin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.manduin.data.repository.ApiDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {
    fun getTravelNewsData() = apiDataRepository.getTravelNewsData().asLiveData()
}