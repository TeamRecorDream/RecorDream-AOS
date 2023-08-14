package com.team.recordream.di // ktlint-disable package-name

import com.team.recordream.data.api.*
import com.team.recordream.data.datasource.remote.*
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
    fun providesHomeDataSourceImpl(
        homeService: HomeService,
    ): HomeDataSource = HomeDateSourceImpl(homeService)

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

    @Provides
    @Singleton
    fun providesRecordDataSourceImpl(
        recordService: RecordService,
    ): RecordDataSource = RecordDataSourceImpl(recordService)

    @Provides
    @Singleton
    fun providesDocumentDataSourceImpl(
        documentService: DocumentService
    ): DocumentDataSource = DocumentDataSourceImpl(documentService)

    @Provides
    @Singleton
    fun providesSearchDataSourceImpl(
        searchService: SearchService,
    ): SearchDataSource = SearchDataSourceImpl(searchService)
}
