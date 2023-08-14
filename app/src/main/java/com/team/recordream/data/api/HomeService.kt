package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.GET

interface HomeService {
    @GET("record")
    suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>
}
