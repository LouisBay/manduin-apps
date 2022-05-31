package com.bangkit.manduin.data.repository

import com.bangkit.manduin.data.remote.retrofit.NewsApiService
import com.bangkit.manduin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    private val newsApiService: NewsApiService
) {
    fun getTravelNewsData() = flow {
        emit(Result.Loading)
        newsApiService.getTravelNews().let {
            if (it.success == true) emit(Result.Success(it.data.posts))
            else emit(Result.Error(it.message.toString()))
        }
    }.catch { emit(Result.Error(it.localizedMessage?.toString() ?: it.message.toString()))
    }.flowOn(Dispatchers.IO)
}