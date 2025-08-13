package com.eello.baeuja.di

import com.eello.baeuja.data.auth.repository.AuthRepositoryImpl
import com.eello.baeuja.data.repository.UserRepositoryImpl
import com.eello.baeuja.domain.auth.repository.AuthRepository
import com.eello.baeuja.domain.repository.UserRepository
import com.eello.baeuja.retrofit.repository.ContentRepository
import com.eello.baeuja.retrofit.repository.ContentRepositoryImpl
import com.eello.baeuja.retrofit.repository.LearningRepository
import com.eello.baeuja.retrofit.repository.LearningRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindContentRepository(impl: ContentRepositoryImpl): ContentRepository

    @Binds
    @Singleton
    abstract fun bindLearningRepository(impl: LearningRepositoryImpl): LearningRepository
}