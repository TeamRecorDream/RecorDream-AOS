package com.recodream_aos.recordream.di // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.*
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

    @Provides
    @Singleton
    fun providesStorageService(
        retrofit: Retrofit
    ): StorageService = retrofit.create(StorageService::class.java)

    @Provides
    @Singleton
    fun providesMypageUserService(
        retrofit: Retrofit
    ): MypageService = retrofit.create(MypageService::class.java)

    @Provides
    @Singleton
    fun providesHomeService(
        retrofit: Retrofit
    ): HomeService = retrofit.create(HomeService::class.java)

    //    @Provides
//    @Singleton
//    fun providesDocumentService(
//        retrofit: Retrofit
//    ): HomeService = retrofit.create(DocumentService::class.java)
    @Provides
    @Singleton
    fun providesRecordService(
        retrofit: Retrofit
    ): RecordService = retrofit.create(RecordService::class.java)

    @Provides
    @Singleton
    fun providesSearchService(
        retrofit: Retrofit
    ): SearchService = retrofit.create(SearchService::class.java)
}
