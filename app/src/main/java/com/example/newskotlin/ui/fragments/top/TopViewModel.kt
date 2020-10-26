package com.example.newskotlin.ui.fragments.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newskotlin.BaseViewModel
import com.example.newskotlin.models.Articles
import com.example.newskotlin.models.ResponseBody
import com.example.newskotlin.network.Resource
import com.example.newskotlin.repository.NewsRepository

class TopViewModel(private val repository: NewsRepository) : BaseViewModel() {
    var page = 0
    var isLastPage = false
    var isLoading = false
    var getDB = MutableLiveData<List<Articles>>()

    var isPagination = MutableLiveData<Boolean>()

    fun getNews() : LiveData<Resource<ResponseBody>> {
        page += 1
        return repository.getTopHeadlines()
    }
    fun fetchFav(){
        getDB.value = repository.fetchFavorites().value
    }
    fun deleteFromFav(){

    }
}