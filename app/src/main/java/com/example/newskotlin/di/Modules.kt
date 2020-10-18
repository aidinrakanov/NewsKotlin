package com.example.newskotlin.di

import com.example.newskotlin.network.RetrofitClient
import com.example.newskotlin.repository.NewsRepository
import com.example.newskotlin.ui.fragments.every.EverythinsViewModel
import com.example.newskotlin.ui.fragments.top.TopViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

var newsModule : Module = module{
    single {RetrofitClient().provideNews()}
    factory { NewsRepository(get()) }

    viewModel { EverythinsViewModel(get()) }
    viewModel { TopViewModel(get()) }

}