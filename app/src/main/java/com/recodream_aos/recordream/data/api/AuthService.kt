package com.recodream_aos.recordream.data.api // ktlint-disable package-name

import com.recodream_aos.recordream.data.entity.remote.request.RequestLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun postLogin(
        @Body requestLogin: RequestLogin
    ): ResponseWrapper<ResponseLogin>

    @POST("/auth/token")
    suspend fun postToken(
        @Header("access") access: String,
        @Header("refresh") refresh: String
    ): ResponseWrapper<ResponseNewToken>
}
