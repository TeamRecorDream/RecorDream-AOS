package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSource
import com.recodream_aos.recordream.data.datasource.remote.AuthDataSource
import com.recodream_aos.recordream.data.repository.AuthRepositoryImpl
import com.recodream_aos.recordream.data.repository.RecordRepositoryImpl
import com.recodream_aos.recordream.domain.repository.AuthRepository
import com.recodream_aos.recordream.domain.repository.RecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesLoginRepository(
        sharedPreferenceDataSource: SharedPreferenceDataSource,
        authDataSource: AuthDataSource
    ): AuthRepository = AuthRepositoryImpl(authDataSource, sharedPreferenceDataSource)

    @Provides
    @Singleton
    fun providesRecordRepository(): RecordRepository = RecordRepositoryImpl()
}
