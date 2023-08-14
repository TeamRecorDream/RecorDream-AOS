package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface HomeDataSource {
    suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>
}
