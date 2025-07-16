package com.eello.baeuja.di

import com.eello.baeuja.auth.TokenAuthInterceptor
import com.eello.baeuja.auth.TokenAuthenticator
import com.eello.baeuja.auth.TokenManager
import com.eello.baeuja.retrofit.api.AuthAPI
import com.eello.baeuja.retrofit.core.ApiResponseCode
import com.eello.baeuja.retrofit.core.ApiResponseCodeAdapter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val BASE_URL = "https://api.baeuja.xyz/api/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): TokenAuthInterceptor =
        TokenAuthInterceptor { tokenManager.accessToken }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenAuthenticator: TokenAuthenticator,
        tokenAuthInterceptor: TokenAuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(tokenAuthenticator)
        .addInterceptor(tokenAuthInterceptor) // 매 요청마다 토큰을 헤더에 추가하는 인터셉터
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gsonWithAdapters = GsonBuilder()
            .registerTypeAdapter(ApiResponseCode::class.java, ApiResponseCodeAdapter())
            .serializeNulls()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonWithAdapters))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI =
        retrofit.create(AuthAPI::class.java)

    // 토큰 갱신용 Authenticator 없는 Retrofit
    @Provides
    @Singleton
    @Named("refresh")
    fun provideRefreshRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 토큰 갱신용 AuthAPI
    @Provides
    @Singleton
    @Named("refresh")
    fun provideRefreshAuthAPI(
        @Named("refresh") retrofit: Retrofit
    ): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }
}