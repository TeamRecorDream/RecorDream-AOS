package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.GET

interface MypageService {
    @GET("user")
    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>
}
