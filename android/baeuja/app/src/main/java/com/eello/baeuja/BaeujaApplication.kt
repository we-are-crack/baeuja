package com.eello.baeuja

import android.app.Application
import com.eello.baeuja.auth.TokenManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BaeujaApplication : Application() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        CoroutineScope(Dispatchers.Default).launch {
            tokenManager.loadTokensToMemory()
        }
    }
}