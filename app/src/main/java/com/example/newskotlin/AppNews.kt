package com.example.newskotlin

import android.app.Application
import com.example.newskotlin.network.NewsApi
import com.example.newskotlin.network.RetrofitClient

class AppNews : Application() {

        val retrofitClient = RetrofitClient()

        fun provideNews() = retrofitClient.provideRetrofit.create(NewsApi::class.java)

    override fun onCreate() {
        super.onCreate()
        provideNews()
    }
}