package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.response.NonNullResponseWrapper
import com.team.recordream.data.entity.remote.response.ResponseHome

interface HomeDataSource {
    suspend fun getHomeRecord(): NonNullResponseWrapper<ResponseHome>
}
