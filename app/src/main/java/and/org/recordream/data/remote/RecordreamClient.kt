package and.org.recordream.data.remote

import and.org.recordream.data.remote.api.RecorDreamService
import and.org.recordream.data.remote.api.StorageService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RecordreamClient {
    private const val BASE_URL = "http://13.125.138.47:8000/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val storageService: StorageService = retrofit.create(StorageService::class.java)

    val recorDreamServicee: RecorDreamService = retrofit.create(RecorDreamService::class.java)
}
