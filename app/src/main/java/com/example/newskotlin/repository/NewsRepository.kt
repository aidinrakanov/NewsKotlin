package com.example.newskotlin.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.newskotlin.Constants
import com.example.newskotlin.models.Articles
import com.example.newskotlin.models.ResponseBody
import com.example.newskotlin.AppNews
import com.example.newskotlin.network.NewsApi
import com.example.newskotlin.network.Resource
import com.example.newskotlin.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(val newsApi: NewsApi) {

    private val defaultSize = 10
    private val ru = "ru"

   fun fetchEverythings(query: String?, page : Int) = liveData(Dispatchers.IO) {
       emit(Resource.loading(data = null))
       try {
           emit(Resource.success(data = newsApi.fetchEverything(query, Constants.apiKey, defaultSize ,page)))
       }catch (ex : Exception){
           emit(Resource.error(data = null, message = ex.message ?: "Error every"))
       }
   }

   fun getTopHeadlines() = liveData(Dispatchers.IO) {
       emit(Resource.loading(data = null))
       try {
           emit(Resource.success(data = newsApi.getNews(ru, Constants.apiKey, defaultSize , 0)))
       }catch (ex : Exception){
           emit(Resource.error(data = null, message = ex.message ?: "Error top"))
       }
   }
}