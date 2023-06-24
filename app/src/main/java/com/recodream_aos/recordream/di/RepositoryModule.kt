package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSource
import com.recodream_aos.recordream.data.datasource.remote.AuthDataSource
import com.recodream_aos.recordream.data.datasource.remote.HomeDataSource
import com.recodream_aos.recordream.data.datasource.remote.MypageDateSource
import com.recodream_aos.recordream.data.datasource.remote.RecordDataSource
import com.recodream_aos.recordream.data.datasource.remote.SearchDataSource
import com.recodream_aos.recordream.data.datasource.remote.StorageDateSource
import com.recodream_aos.recordream.data.repository.AuthRepositoryImpl
import com.recodream_aos.recordream.data.repository.MypageUserRepositoryImpl
import com.recodream_aos.recordream.data.repository.RecordRepositoryImpl
import com.recodream_aos.recordream.data.repository.SearchRepositoryImpl
import com.recodream_aos.recordream.data.repository.StorageRepositoryImpl
import com.recodream_aos.recordream.domain.repository.AuthRepository
import com.recodream_aos.recordream.domain.repository.MypageUserRepository
import com.recodream_aos.recordream.domain.repository.RecordRepository
import com.recodream_aos.recordream.domain.repository.SearchRepository
import com.recodream_aos.recordream.domain.repository.StorageRepository
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
        authDataSource: AuthDataSource,
    ): AuthRepository = AuthRepositoryImpl(authDataSource, sharedPreferenceDataSource)

    @Provides
    @Singleton
    fun providesStorageRepository(
        storageDateSource: StorageDateSource,
    ): StorageRepository = StorageRepositoryImpl(storageDateSource)

    @Provides
    @Singleton
    fun providesMypageUserRepository(
        mypageDataSource: MypageDateSource,
    ): MypageUserRepository = MypageUserRepositoryImpl(mypageDataSource)

    @Provides
    @Singleton
    fun providesHomeRepository(
        homeDataSource: HomeDataSource
    ): HomeRepository = HomeRepositoryImpl(homeDataSource)

    @Provides
    @Singleton
    fun providesRecordRepository(): RecordRepository = RecordRepositoryImpl()
    fun providesRecordRepository(
        recordDataSource: RecordDataSource,
    ): RecordRepository = RecordRepositoryImpl(recordDataSource)

    @Provides
    @Singleton
    fun providesSearchRepository(
        searchDataSource: SearchDataSource,
    ): SearchRepository = SearchRepositoryImpl(searchDataSource)
}
