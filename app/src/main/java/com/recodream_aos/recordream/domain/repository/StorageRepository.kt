package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface StorageRepository {

    suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>?
}
