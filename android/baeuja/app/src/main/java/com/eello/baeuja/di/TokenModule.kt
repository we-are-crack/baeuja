package com.eello.baeuja.di

import com.eello.baeuja.data.auth.service.TokenManagerImpl
import com.eello.baeuja.data.auth.service.TokenRefresherImpl
import com.eello.baeuja.domain.auth.service.TokenManager
import com.eello.baeuja.domain.auth.service.TokenRefresher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    @Singleton
    abstract fun bindTokenManager(impl: TokenManagerImpl): TokenManager

    @Binds
    @Singleton
    abstract fun bindTokenRefresher(impl: TokenRefresherImpl): TokenRefresher
}