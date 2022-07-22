package and.org.recordream.data.remote

import and.org.recordream.data.remote.api.HomeService
import and.org.recordream.data.remote.api.MypageService
import and.org.recordream.data.remote.api.RecorDreamService
import and.org.recordream.data.remote.api.StorageService
import and.org.recordream.data.remote.api.WriteService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object RecordreamClient {
    private const val BASE_URL = "http://13.125.138.47:8000/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val storageService: StorageService = retrofit.create(StorageService::class.java)
    val writeService: WriteService = retrofit.create(WriteService::class.java)
    val mypageService: MypageService = retrofit.create(MypageService::class.java)
    val recorDreamServicee: RecorDreamService = retrofit.create(RecorDreamService::class.java)

    val homeService: HomeService = retrofit.create(HomeService::class.java)
}

private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient =
    OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        build()
    }

class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("userId", "1")
            .build()
        proceed(newRequest)
    }
}