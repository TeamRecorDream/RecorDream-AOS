package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface StorageDateSource {

    suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>
}