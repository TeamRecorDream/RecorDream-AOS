package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.api.MypageService
import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestNickName
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseAlamToggle
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
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
