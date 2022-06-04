package com.bangkit.manduin.data.repository

import com.bangkit.manduin.data.remote.retrofit.ManduinApiService
import com.bangkit.manduin.data.remote.retrofit.NewsApiService
import com.bangkit.manduin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val manduinApiService: ManduinApiService
) {
    fun getTravelNewsData() = flow {
        emit(Result.Loading)
        newsApiService.getTravelNews().let {
            if (it.success == true) emit(Result.Success(it.data.posts))
            else emit(Result.Error(it.message.toString()))
        }
    }.catch { emit(Result.Error(it.localizedMessage?.toString() ?: it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getAllLandmark() = flow {
        emit(Result.Loading)
        manduinApiService.getAllLandmark().let {
            if (it.data != null) emit(Result.Success(it.data))
        }
    }.catch { emit(Result.Error(it.localizedMessage?.toString() ?: it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getLandmarkDetail(id: Int) = flow {
        emit(Result.Loading)
        manduinApiService.getLandmarkDetail(id).let {
            if (it != null) emit(Result.Success(it))
        }
    }.catch { emit(Result.Error(it.localizedMessage?.toString() ?: it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTourismPlaceDetail(id: Int) = flow {
        emit(Result.Loading)
        manduinApiService.getTourismPlaceDetail(id).let {
            if (it != null) emit(Result.Success(it))
        }
    }.catch { emit(Result.Error(it.localizedMessage?.toString() ?: it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getNearestTourismLocFromLandmark(label: String) = flow {
        emit(Result.Loading)
        manduinApiService.getNearestTourismLocFromLandmark(label).let {
            if (it.data != null) emit(Result.Success(it.data))
        }
    }.catch { emit(Result.Error(it.localizedMessage?.toString() ?: it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTourismPlaceAtProvince(search: String) = flow {
        emit(Result.Loading)
        try {
            manduinApiService.getTourismPlaceAtProvince(search).let {
                if (it.data != null) emit(Result.Success(it.data))
            }
        } catch (e: HttpException) {
            val message = String.format("%s|%s", (e.localizedMessage?.toString() ?: e.message.toString()), e.code())
            emit(Result.Error(message))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}