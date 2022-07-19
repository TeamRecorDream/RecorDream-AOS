package and.org.recordream.data.remote

import and.org.recordream.data.remote.api.StorageService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecordreamClient {
    private const val BASE_URL = "13.125.138.47:8000/"

    val customRetrofit: StorageService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StorageService::class.java)
}