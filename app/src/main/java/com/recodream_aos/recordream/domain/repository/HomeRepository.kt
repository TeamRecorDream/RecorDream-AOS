package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface HomeRepository {
    suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>?
}
