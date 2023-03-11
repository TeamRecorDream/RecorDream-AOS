package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface MypageDateSource {
    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>
    suspend fun postPushAlam(alamTime: RequestPushAlam): NoDataResponse
}
