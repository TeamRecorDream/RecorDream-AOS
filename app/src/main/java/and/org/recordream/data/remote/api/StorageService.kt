package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.ResponseRecords
import and.org.recordream.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StorageService {


    @GET("record/storage/list")
    fun getMyRecord(
        @Query("filter") num: Int,
    ): Call<ResponseWrapper<ResponseRecords>>


    @GET("record/storage/list")
    fun getMyGalleryRecord(
        @Query("filter") num: Int,
    ): Call<ResponseWrapper<ResponseRecords>>


    @GET("record/storage/list")
    fun getMyListRecord(
        @Query("filter") num: Int,
    ): Call<ResponseWrapper<ResponseRecords>>

}