package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.AuthService
import com.recodream_aos.recordream.data.api.MypageService
import com.recodream_aos.recordream.data.api.StorageService
import com.recodream_aos.recordream.data.datasource.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun providesAuthDataSourceImpl(
        authService: AuthService,
    ): AuthDataSource = AuthDataSourceImpl(authService)

    @Provides
    @Singleton
    fun providesStorageDataSourceImpl(
        storageService: StorageService,
    ): StorageDateSource = StorageDateSourceImpl(storageService)

    @Provides
    @Singleton
    fun providesMypageUserDataSourceImpl(
        mypageUserService: MypageService,
    ): MypageDateSource = MypageUserDateSourceImpl(mypageUserService)
}
