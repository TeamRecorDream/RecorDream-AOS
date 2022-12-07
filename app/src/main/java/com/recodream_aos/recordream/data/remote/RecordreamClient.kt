package com.recodream_aos.recordream.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.recodream_aos.recordream.data.remote.api.LoginService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RecordreamClient {
    @OptIn(ExperimentalSerializationApi::class)
    val recordreamRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://13.125.138.47:8000")
            .addConverterFactory(Json.asConverterFactory("Application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = recordreamRetrofit.create<T>(T::class.java)
}

object ServciePool {
    val loginService = RecordreamClient.create<LoginService>()
}
