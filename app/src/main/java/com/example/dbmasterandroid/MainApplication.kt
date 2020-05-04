package com.example.dbmasterandroid

import android.app.Application
import com.example.dbmasterandroid.di.apiModule
import com.example.dbmasterandroid.di.compositeDisposable
import com.example.dbmasterandroid.di.viewModelPart
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    private val modules = listOf(
            viewModelPart,
            compositeDisposable,
            apiModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}