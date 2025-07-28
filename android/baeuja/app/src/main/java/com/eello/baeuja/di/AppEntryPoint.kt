package com.eello.baeuja.di

import com.eello.baeuja.session.HomeContentSession
import com.eello.baeuja.session.UserSession
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppEntryPoint {

    fun userSession(): UserSession

    fun homeContentSession(): HomeContentSession
}