package com.team.recordream.data.datasource.remote

import com.team.recordream.data.api.StorageService
import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class StorageDateSourceImpl @Inject constructor(
    private val storageService: StorageService,
) : StorageDateSource {

    override suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage> {
        return storageService.getStorage(emotionId)
    }
}
