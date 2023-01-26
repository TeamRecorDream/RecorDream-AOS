package com.recodream_aos.recordream.data.datasource.remote

import android.util.Log
import com.recodream_aos.recordream.data.api.StorageService
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class StorageDateSourceImpl @Inject constructor(
    private val storageService: StorageService
) : StorageDateSource {

    override suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage> {
        Log.d("babo2", "getStorage: ")
        Log.d("babo3", "getStorage: ${storageService.getStorage(2)}")
        return storageService.getStorage(emotionId)
    }
}
