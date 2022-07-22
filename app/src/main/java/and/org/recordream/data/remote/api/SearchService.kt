package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.ResponseRecords
import and.org.recordream.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("record/storage/search")
    fun getMyRecord(
        @Query("keyword") keyword: String,
    ): Call<ResponseWrapper<ResponseRecords>>

}