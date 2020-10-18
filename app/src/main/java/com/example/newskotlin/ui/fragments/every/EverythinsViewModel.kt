package com.example.newskotlin.ui.fragments.every


import androidx.lifecycle.MutableLiveData
import com.example.newskotlin.BaseViewModel
import com.example.newskotlin.models.ResponseBody
import com.example.newskotlin.network.Resource
import com.example.newskotlin.repository.NewsRepository


class EverythinsViewModel (private val repository: NewsRepository) : BaseViewModel() {

   var page = 0

    var articles = MutableLiveData<Resource<ResponseBody>>()
    var isPagination = MutableLiveData<Boolean>()

    fun fetchEverything(query: String) {
        page += 1
        articles = repository.fetchEverythings("android", page) as
                MutableLiveData<Resource<ResponseBody>>
    }
}