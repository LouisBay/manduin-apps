package com.bangkit.manduin.viewmodel

import androidx.lifecycle.*
import com.bangkit.manduin.data.local.entity.PlaceEntity
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.data.repository.DatabaseRepository
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.utils.StateAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    fun getAllBookmarkedPlace() = databaseRepository.getAllBookmarkedPlace()

    fun deletePlaceFromBookmark(place: PlaceEntity) {
        viewModelScope.launch {
            databaseRepository.deletePlaceFromBookmark(place)
        }
    }
}