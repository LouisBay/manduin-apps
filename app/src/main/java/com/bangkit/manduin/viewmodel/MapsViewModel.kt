package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.MapsTourismPlaceItem
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {

    private val _resultLandmark = MutableLiveData<Result<LandmarkItem>>()
    val resultLandmark: LiveData<Result<LandmarkItem>> = _resultLandmark

    private val _resultWisataMaps = MutableLiveData<Result<ArrayList<MapsTourismPlaceItem>>>()
    val resultWisataMaps: LiveData<Result<ArrayList<MapsTourismPlaceItem>>> = _resultWisataMaps

    fun getLandmark(id: Int) {
        viewModelScope.launch {
            apiDataRepository.getLandmarkDetail(id).collect {
                _resultLandmark.value = it
            }
        }
    }

    fun getNearestTourismLocFromLandmark(label: String) {
        viewModelScope.launch {
            apiDataRepository.getNearestTourismLocFromLandmark(label).collect {
                _resultWisataMaps.value = it
            }
        }
    }

}