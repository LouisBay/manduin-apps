package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.local.entity.PlaceEntity
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.TourismPlaceItem
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.data.repository.DatabaseRepository
import com.bangkit.manduin.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiDataRepository: ApiDataRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _isBookmarked = MutableLiveData(false)
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    private val _resultLandmark = MutableLiveData<Result<LandmarkItem>>()
    val resultLandmark: LiveData<Result<LandmarkItem>> = _resultLandmark

    private val _resultTourismPlace = MutableLiveData<Result<TourismPlaceItem>>()
    val resultTourismPlace: LiveData<Result<TourismPlaceItem>> = _resultTourismPlace

    fun checkBookmarked(id: Int) {
        viewModelScope.launch {
            databaseRepository.checkBookmarked(id).collect {
                _isBookmarked.value = it
            }
        }
    }

    fun insertPlaceToBookmark(place: PlaceEntity) {
        viewModelScope.launch {
            databaseRepository.insertPlaceToBookmark(place)
        }
    }

    fun deletePlaceFromBookmark(place: PlaceEntity) {
        viewModelScope.launch {
            databaseRepository.deletePlaceFromBookmark(place)
        }
    }

    fun getLandmarkDetail(id: Int) {
        viewModelScope.launch {
            apiDataRepository.getLandmarkDetail(id).collect {
                _resultLandmark.value = it
            }
        }
    }

    fun getTourismPlaceDetail(id: Int) {
        viewModelScope.launch {
            apiDataRepository.getTourismPlaceDetail(id).collect {
                _resultTourismPlace.value = it
            }
        }
    }
}