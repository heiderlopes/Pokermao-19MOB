package com.example.pokermao

import android.app.Application
import com.example.pokermao.di.networkModule
import com.example.pokermao.di.repositoryModule
import com.example.pokermao.di.viewModelModule
import com.example.pokermao.di.viewModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)


        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    viewModule
                )
            )
        }
    }
}