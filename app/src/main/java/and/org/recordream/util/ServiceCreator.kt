package and.org.recordream.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ServiceCreator {
    private const val BASE_URL = "http://13.125.138.47:8000/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val recorDreamService: RecorDreamService = retrofit.create(RecorDreamService::class.java)
}

// 헤더 추가하는 방법 찾아서 넣었는데 잘못된 부분 있으면 수정 부탁합니다..!
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