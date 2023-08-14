package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.request.RequestAlamToggle
import com.team.recordream.data.entity.remote.request.RequestNickName
import com.team.recordream.data.entity.remote.request.RequestPushAlam
import com.team.recordream.data.entity.remote.response.NoDataResponse
import com.team.recordream.data.entity.remote.response.ResponseAlamToggle
import com.team.recordream.data.entity.remote.response.ResponseMypageUser
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface MypageDateSource {
    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>
    suspend fun postPushAlam(alamTime: RequestPushAlam): NoDataResponse

    suspend fun patchAlamToggle(isActive: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle>
    suspend fun putUserName(nickName: RequestNickName): NoDataResponse
}
