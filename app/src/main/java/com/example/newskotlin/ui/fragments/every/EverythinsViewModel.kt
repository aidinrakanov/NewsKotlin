package com.example.newskotlin.ui.fragments.every


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newskotlin.BaseViewModel
import com.example.newskotlin.models.Articles
import com.example.newskotlin.models.ResponseBody
import com.example.newskotlin.network.Resource
import com.example.newskotlin.repository.NewsRepository


class EverythinsViewModel (private val repository: NewsRepository) : BaseViewModel() {

    var isLastPage = false
    var isLoading = false
    var page = 0
    var getDB = MutableLiveData<List<Articles>>()

    var articles = MutableLiveData<ResponseBody>()
    var isPagination = MutableLiveData<Boolean>()

    fun fetchEverything(query: String) : LiveData<Resource<ResponseBody>> {
        page += 1
       return repository.fetchEverythings("bitcoin", page)
    }

    fun fetchFav(){
       getDB.value = repository.fetchFavorites().value
    }

    fun insertFav(articles: Articles) {
        repository.insertNewNews(articles)
    }
}