package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.MapsTourismPlaceItem
import com.bangkit.manduin.data.remote.response.TourismPlaceItem
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourismViewModel @Inject constructor(
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {

    private val _resultListWisata = MutableLiveData<Result<ArrayList<TourismPlaceItem>>>()
    val resultListWisata: LiveData<Result<ArrayList<TourismPlaceItem>>> = _resultListWisata

    fun getTourismPlaceAtProvince(search: String) {
        viewModelScope.launch {
            apiDataRepository.getTourismPlaceAtProvince(search).collect {
                _resultListWisata.value = it
            }
        }
    }

}