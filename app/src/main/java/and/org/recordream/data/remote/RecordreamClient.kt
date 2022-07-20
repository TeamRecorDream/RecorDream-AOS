package and.org.recordream.data.remote

import and.org.recordream.data.remote.api.MypageService
import and.org.recordream.data.remote.api.StorageService
import and.org.recordream.data.remote.api.WriteService
import and.org.recordream.presentation.write.WriteActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object RecordreamClient {
    private const val BASE_URL = "http://13.125.138.47:8000/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val storageService: StorageService = retrofit.create(StorageService::class.java)
    val mypageService: MypageService = retrofit.create(MypageService::class.java)
    val writeService:WriteService = retrofit.create(WriteService::class.java)
}
