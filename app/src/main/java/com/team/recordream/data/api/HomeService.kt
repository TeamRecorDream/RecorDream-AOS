package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.response.NonNullResponseWrapper
import com.team.recordream.data.entity.remote.response.ResponseHome
import retrofit2.http.GET

interface HomeService {
    @GET("record")
    suspend fun getHomeRecord(): NonNullResponseWrapper<ResponseHome>
}
