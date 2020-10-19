package com.example.newskotlin

import android.app.Application
import com.example.newskotlin.di.newsModule
import com.example.newskotlin.network.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppNews : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppNews)
            newsModule}
    }
}