package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSource
import com.recodream_aos.recordream.data.datasource.remote.*
import com.recodream_aos.recordream.data.repository.*
import com.recodream_aos.recordream.domain.repository.*
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
    fun providesStorageRepository(
        storageDateSource: StorageDateSource
    ): StorageRepository = StorageRepositoryImpl(storageDateSource)

    @Provides
    @Singleton
    fun providesMypageUserRepository(
        mypageDataSource: MypageDateSource
    ): MypageUserRepository = MypageUserRepositoryImpl(mypageDataSource)

    @Provides
    @Singleton
    fun providesHomeRepository(
        homeDataSource: HomeDataSource
    ): HomeRepository = HomeRepositoryImpl(homeDataSource)

    @Provides
    @Singleton
    fun providesDocumentRepository(
        documentDataSource: DocumentDataSource
    ): DocumentRepository = DocumentRepositoryImpl(documentDataSource)

    @Provides
    @Singleton
    fun providesRecordRepository(
        recordDataSource: RecordDataSource
    ): RecordRepository = RecordRepositoryImpl(recordDataSource)

    @Provides
    @Singleton
    fun providesSearchRepository(
        searchDataSource: SearchDataSource
    ): SearchRepository = SearchRepositoryImpl(searchDataSource)
}
