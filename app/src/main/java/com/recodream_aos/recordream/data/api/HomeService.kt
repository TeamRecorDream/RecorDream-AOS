package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {
    @GET("record")
    fun getHomeRecord(): Call<ResponseWrapper<ResponseHome>>
}