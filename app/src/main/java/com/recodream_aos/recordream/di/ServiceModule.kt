package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesLoginService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)
}