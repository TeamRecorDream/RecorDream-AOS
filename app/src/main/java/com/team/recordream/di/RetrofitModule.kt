package com.team.recordream.di // ktlint-disable package-name

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team.recordream.BuildConfig.* // ktlint-disable no-wildcard-imports
import com.team.recordream.util.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val CONTENT_TYPE = "Application/json"

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(RECORDREAM_BASE_URL)
            .client(client)
            .addConverterFactory(
                Json.asConverterFactory(
                    requireNotNull(
                        CONTENT_TYPE.toMediaTypeOrNull(),
                    ),
                ),
            )
            .build()

    @Provides
    @Singleton
    fun providesOkhttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        )
        .addInterceptor(authInterceptor)
        .build()
}
