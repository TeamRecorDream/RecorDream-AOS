package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestNickName
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseAlamToggle
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.*

interface MypageService {
    @GET("user")
    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>

    @POST("user/notice")
    suspend fun postPushAlam(@Body requestTime: RequestPushAlam): NoDataResponse

    @PATCH("user/toggle")
    suspend fun patchToggle(@Body requestAlamToggle: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle>

    //    @DELETE("user")
//    suspend fun deleteUser():ResponseWrapper

    @PUT("user/nickname")
    suspend fun putUserName(@Body nickName: RequestNickName): NoDataResponse


}
