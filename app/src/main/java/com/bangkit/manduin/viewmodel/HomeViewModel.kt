package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.utils.StateAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {

    private val _isNewsFetched = MutableLiveData(false)
    val isNewsFetched: LiveData<Boolean> = _isNewsFetched

    private val _isLandmarksFetched = MutableLiveData(false)
    val isLandmarksFetched: LiveData<Boolean> = _isLandmarksFetched

    private val _resultlistNews = MutableLiveData<Result<ArrayList<NewsItem>>>()
    val resultlistNews: LiveData<Result<ArrayList<NewsItem>>> = _resultlistNews

    private val _resultlistLandmark = MutableLiveData<Result<ArrayList<LandmarkItem>>>()
    val resultlistLandmark: LiveData<Result<ArrayList<LandmarkItem>>> = _resultlistLandmark

    fun getTravelNewsData() {
        viewModelScope.launch {
            apiDataRepository.getTravelNewsData().collect {
                _resultlistNews.value = it
            }
        }
    }

    fun getAllLandmark() {
        viewModelScope.launch {
            apiDataRepository.getAllLandmark().collect {
                _resultlistLandmark.value = it
            }
        }
    }

    fun newsFetched() {
        viewModelScope.launch {
            _isNewsFetched.value = true
        }
    }

    fun landmarksFetched() {
        viewModelScope.launch {
            _isLandmarksFetched.value = true
        }
    }
}