package com.bangkit.manduin.viewmodel

import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.manduin.data.repository.MLRepository
import com.bangkit.manduin.model.LabelModel
import com.bangkit.manduin.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val mlRepository: MLRepository
) : ViewModel() {
    private val _result = MutableLiveData<Result<LabelModel>>()
    val result: LiveData<Result<LabelModel>> = _result

    fun detectImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            mlRepository.getItemDetected(imageProxy).collect {
                _result.value = it
                Log.d("TEST", it.toString())
            }
        }
    }
}