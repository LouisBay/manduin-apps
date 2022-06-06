package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {

    private val _isNewsFetched = MutableLiveData(false)
    val isNewsFetched: LiveData<Boolean> = _isNewsFetched

    private val _resultlistNews = MutableLiveData<Result<ArrayList<NewsItem>>>()
    val resultlistNews: LiveData<Result<ArrayList<NewsItem>>> = _resultlistNews

    fun getTravelNewsData() {
        viewModelScope.launch {
            apiDataRepository.getTravelNewsData().collect {
                _resultlistNews.value = it
            }
        }
    }

    fun newsFetched() {
        viewModelScope.launch {
            _isNewsFetched.value = true
        }
    }
}