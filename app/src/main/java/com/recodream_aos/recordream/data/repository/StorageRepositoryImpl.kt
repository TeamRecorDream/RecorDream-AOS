package com.recodream_aos.recordream.data.repository

import com.recodream_aos.recordream.data.datasource.remote.StorageDateSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageDateSource: StorageDateSource,
) : StorageRepository {

    override suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>? {
        // todo response 값이 오는 곳
        try {
            val response = storageDateSource.getStorage(emotionId)
            return response
        } catch (e: Exception) {
            return null
        }
    }
}
