package com.eello.baeuja.di

import com.eello.baeuja.retrofit.core.ApiErrorBodyParser
import com.eello.baeuja.retrofit.core.ApiErrorBodyParserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApiBindModule {

    @Binds
    @Singleton
    fun bindApiErrorParser(impl: ApiErrorBodyParserImpl): ApiErrorBodyParser
}