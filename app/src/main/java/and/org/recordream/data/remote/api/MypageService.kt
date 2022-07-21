package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.request.RequesMypageNickname
import and.org.recordream.data.remote.request.RequestMypagePushModify
import and.org.recordream.data.remote.request.RequestMypagePutTime
import and.org.recordream.data.remote.response.ResponseMypagePutTime
import and.org.recordream.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface MypageService {
    //푸시알림
    @POST("/notice")
    fun postPushTime(
        @Header("userId") userId: Int = 1,
        @Body body: RequestMypagePutTime
    ): Call<ResponseWrapper<ResponseMypagePutTime>>

    @PUT("/user/nickname")
    fun putEditNickname(    //닉네임 수정
        @Header("userId") userId: Int = 1,
        @Body post: RequesMypageNickname
    ): Call<RequesMypageNickname?>?

    @PUT("/notice/:noticeId")
    fun putPushModify(    //푸시알림 시간 수정
        @Header("userId") userId: Int = 1,
        @Body post: RequestMypagePushModify
    ): Call<RequestMypagePushModify?>?

    @PUT("/user/:userId/fcm-token")
    fun putRefreshToken(    //푸시알림 시간 수정
        @Header("userId") userId: Int = 1,
        @Body post: RequestMypagePushModify
    ): Call<RequestMypagePushModify?>?


}
