package com.recodream_aos.recordream.data.repository

import android.util.Log
import com.recodream_aos.recordream.data.datasource.remote.StorageDateSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageDateSource: StorageDateSource
) : StorageRepository {

    override suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>? {
//        todo response 값이 오는 곳
        try {
            val response = storageDateSource.getStorage(emotionId)
            Log.d("babo", "getStorage: $response")
            return response
        } catch (e: Exception) {
            Log.d("babo1", "getStorage: 왜 null임 $e")
            return null
        }
    }
}
