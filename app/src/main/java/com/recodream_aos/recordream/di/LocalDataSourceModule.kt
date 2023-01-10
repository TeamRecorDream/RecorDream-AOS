package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.datasource.local.RecordreamSharedPreference
import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSource
import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideSharedPreferenceImpl(): SharedPreferenceDataSource =
        SharedPreferenceDataSourceImpl(RecordreamSharedPreference)
}
