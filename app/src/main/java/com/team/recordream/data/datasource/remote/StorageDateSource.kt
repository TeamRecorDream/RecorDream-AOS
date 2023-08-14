package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface StorageDateSource {

    suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>
}
