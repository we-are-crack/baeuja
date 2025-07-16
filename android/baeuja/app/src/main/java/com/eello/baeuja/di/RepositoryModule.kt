package com.eello.baeuja.di

import com.eello.baeuja.retrofit.repository.AuthRepository
import com.eello.baeuja.retrofit.repository.AuthRepositoryImpl
import com.eello.baeuja.retrofit.repository.UserRepository
import com.eello.baeuja.retrofit.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}