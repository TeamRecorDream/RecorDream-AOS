package com.team.recordream.data.datasource.remote

import com.team.recordream.data.api.MypageService
import com.team.recordream.data.entity.remote.request.RequestAlamToggle
import com.team.recordream.data.entity.remote.request.RequestNickName
import com.team.recordream.data.entity.remote.request.RequestPushAlam
import com.team.recordream.data.entity.remote.response.NoDataResponse
import com.team.recordream.data.entity.remote.response.ResponseAlamToggle
import com.team.recordream.data.entity.remote.response.ResponseMypageUser
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class MypageUserDateSourceImpl @Inject constructor(
    private val mypageService: MypageService,
) : MypageDateSource {
    override suspend fun getUser(): ResponseWrapper<ResponseMypageUser> {
        return mypageService.getUser()
    }

    override suspend fun postPushAlam(alamTime: RequestPushAlam): NoDataResponse {
        return mypageService.postPushAlam(alamTime)
    }

    override suspend fun patchAlamToggle(isActive: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle> {
        return mypageService.patchToggle(isActive)
    }

    override suspend fun putUserName(nickName: RequestNickName): NoDataResponse {
        return mypageService.putUserName(nickName)
    }
}
