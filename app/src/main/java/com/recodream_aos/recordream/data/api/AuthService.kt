package com.recodream_aos.recordream.data.api // ktlint-disable package-name

import com.recodream_aos.recordream.data.entity.remote.request.RequestLogin
import com.recodream_aos.recordream.data.entity.remote.response.*
import retrofit2.http.*

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

    @DELETE("user")
    suspend fun deleteUser(): NoDataResponse

    @POST("auth/logout")
    suspend fun patchLogout(): NoDataResponse

}
