package com.harman.imageprocessingmvvm.utilities

import android.app.Application
import com.harman.imageprocessingmvvm.dependencyinjection.repositoryModule
import com.harman.imageprocessingmvvm.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}