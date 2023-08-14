package com.team.recordream.domain.repository

import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface StorageRepository {

    suspend fun getStorage(emotionId: Int): ResponseWrapper<ResponseStorage>?
}
