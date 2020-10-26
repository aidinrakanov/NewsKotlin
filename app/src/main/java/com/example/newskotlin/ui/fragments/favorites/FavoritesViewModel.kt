package com.example.newskotlin.ui.fragments.favorites

import androidx.lifecycle.ViewModel
import com.example.newskotlin.BaseViewModel
import com.example.newskotlin.models.Articles
import com.example.newskotlin.repository.NewsRepository

class FavoritesViewModel(private val repository: NewsRepository) : BaseViewModel() {

    fun remove(articles: Articles){
        repository.newsDao.delete(articles)
    }
}