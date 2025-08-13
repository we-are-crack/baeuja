package com.eello.baeuja.di

import com.eello.baeuja.data.network.TokenAuthInterceptor
import com.eello.baeuja.data.network.TokenAuthenticator
import com.eello.baeuja.domain.auth.service.TokenManager
import com.eello.baeuja.retrofit.adapter.CaseInsensitiveEnumDeserializer
import com.eello.baeuja.retrofit.core.ApiResponseCode
import com.eello.baeuja.retrofit.core.ApiResponseCodeAdapter
import com.eello.baeuja.viewmodel.ContentClassification
import com.google.gson.Gson
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
    fun provideGsonWithAdapters(): Gson = GsonBuilder()
        .registerTypeAdapter(ApiResponseCode::class.java, ApiResponseCodeAdapter())
        .registerTypeAdapter(
            ContentClassification::class.java,
            CaseInsensitiveEnumDeserializer(ContentClassification::class.java)
        )
        .serializeNulls()
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonWithAdapter: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonWithAdapter))
            .build()

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
}