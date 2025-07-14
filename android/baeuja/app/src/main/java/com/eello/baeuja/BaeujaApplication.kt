package com.eello.baeuja

import android.app.Application
import com.eello.baeuja.auth.TokenManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BaeujaApplication : Application() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.Default).launch {
            tokenManager.loadTokensToMemory()
        }
    }
}