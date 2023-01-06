package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.recodream_aos.recordream.BuildConfig
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

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.RECORDREAM_BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    requireNotNull(
                        "Application/json".toMediaTypeOrNull()
                    )
                )
            )
            .build()
}
