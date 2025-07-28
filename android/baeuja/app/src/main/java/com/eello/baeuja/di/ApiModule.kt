package com.eello.baeuja.di

import com.eello.baeuja.retrofit.api.AuthAPI
import com.eello.baeuja.retrofit.api.ContentAPI
import com.eello.baeuja.retrofit.api.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI =
        retrofit.create(AuthAPI::class.java)

    // 토큰 갱신용 AuthAPI
    @Provides
    @Singleton
    @Named("refresh")
    fun provideRefreshAuthAPI(
        @Named("refresh") retrofit: Retrofit
    ): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserAPI(retrofit: Retrofit): UserAPI =
        retrofit.create(UserAPI::class.java)

    @Provides
    @Singleton
    fun provideContentAPI(retrofit: Retrofit): ContentAPI =
        retrofit.create(ContentAPI::class.java)
}