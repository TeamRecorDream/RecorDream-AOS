package com.team.recordream.domain.repository

import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface HomeRepository {
    suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>?
}
