package com.example.newskotlin.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newskotlin.models.Articles
import com.example.newskotlin.repository.NewsRepository

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

   var isLastPage = 0
   var page = 0

    var articles = MutableLiveData<MutableList<Articles>>()


    fun fetchEverything(query: String) {
        page += 1
        articles = newsRepository.fetchEverything(query, page)!!
    }
    fun getNews(page: Int){
       articles = newsRepository.getNews(page)!!
    }

}