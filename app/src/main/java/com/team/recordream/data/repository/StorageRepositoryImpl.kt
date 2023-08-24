package com.team.recordream.data.repository

import com.team.recordream.data.datasource.remote.StorageDateSource
import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import com.team.recordream.domain.repository.StorageRepository
import timber.log.Timber
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageDateSource: StorageDateSource,
) : StorageRepository {

    override suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>? {
//        todo response 값이 오는 곳
        try {
            val response = storageDateSource.getStorage(emotionId)
            return response
        } catch (e: Exception) {
            Timber.tag("checkNetworkError").d("getStorage: 여기인가 $e")
            return null
        }
    }
}
