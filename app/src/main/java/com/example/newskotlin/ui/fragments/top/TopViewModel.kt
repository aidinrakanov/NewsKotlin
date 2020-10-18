package com.example.newskotlin.ui.fragments.top

import androidx.lifecycle.MutableLiveData
import com.example.newskotlin.BaseViewModel
import com.example.newskotlin.models.ResponseBody
import com.example.newskotlin.network.Resource
import com.example.newskotlin.repository.NewsRepository

class TopViewModel(private val repository: NewsRepository) : BaseViewModel() {
    var page = 0

    var articles = MutableLiveData<Resource<ResponseBody>>()
    var isPagination = MutableLiveData<Boolean>()


    fun getNews() {
        page += 1
        articles = repository.getTopHeadlines() as
                MutableLiveData<Resource<ResponseBody>>
    }

}