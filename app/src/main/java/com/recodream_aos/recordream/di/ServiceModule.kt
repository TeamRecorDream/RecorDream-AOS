package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.AuthService
import com.recodream_aos.recordream.data.api.MypageService
import com.recodream_aos.recordream.data.api.StorageService
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
        retrofit: Retrofit,
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesStorageService(
        retrofit: Retrofit,
    ): StorageService = retrofit.create(StorageService::class.java)

    @Provides
    @Singleton
    fun providesMypageUserService(
        retrofit: Retrofit,
    ): MypageService = retrofit.create(MypageService::class.java)
}
