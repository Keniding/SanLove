package com.keniding.sanlove

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.keniding.sanlove.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SanLoveApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()

        Firebase.database.setPersistenceEnabled(true)

        startKoin {
            androidLogger()
            androidContext(this@SanLoveApplication)
            modules(appModule)
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
