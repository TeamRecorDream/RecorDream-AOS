package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MypageService {
    @GET("user")
    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>

    @POST("/user/notice")
    suspend fun postPushAlam(@Body requestTime: RequestPushAlam): ResponseWrapper<String>

}
