package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory // ktlint-disable import-ordering
import com.recodream_aos.recordream.BuildConfig
// import com.recodream_aos.recordream.util.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val CONTENT_TYPE = "Application/json"

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.RECORDREAM_BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    requireNotNull(
                        CONTENT_TYPE.toMediaTypeOrNull()
                    )
                )
            )
            .build()

//    @Provides
//    @Singleton
//    fun providesOkhttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
//        .addInterceptor(
//            HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//        )
//        .addInterceptor(authInterceptor)
//        .build()
}
