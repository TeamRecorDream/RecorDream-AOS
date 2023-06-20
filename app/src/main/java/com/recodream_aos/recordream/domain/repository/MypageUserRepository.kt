package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestNickName
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseAlamToggle
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface MypageUserRepository {

    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>?
    suspend fun postPushAlam(alamTime: RequestPushAlam): Result<NoDataResponse>

    suspend fun patchAlamToggle(isActive: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle>?
    suspend fun putNickName(nickName: RequestNickName): Result<NoDataResponse>
}
