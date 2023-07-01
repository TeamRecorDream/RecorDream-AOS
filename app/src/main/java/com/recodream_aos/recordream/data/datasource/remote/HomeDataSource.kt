package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface HomeDataSource {
    suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>
}
