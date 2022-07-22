package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.ResponseHomeItems
import and.org.recordream.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {
    @GET("record")
    fun getHomeRecord(): Call<ResponseWrapper<ResponseHomeItems>>
}