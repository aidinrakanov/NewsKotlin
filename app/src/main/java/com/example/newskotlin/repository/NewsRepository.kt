package com.example.newskotlin.repository

import androidx.lifecycle.MutableLiveData
import com.example.newskotlin.Constants
import com.example.newskotlin.models.Articles
import com.example.newskotlin.models.ResponseBody
import com.example.newskotlin.AppNews
import com.example.newskotlin.network.NewsApi
import com.example.newskotlin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(val newsApi: NewsApi) {

    private val everythingDefaultSize = 10
    private val ru = "ru"

    fun fetchEverything(query: String?, page: Int) : MutableLiveData<MutableList<Articles>>? {
        var data:MutableLiveData<MutableList<Articles>>? = MutableLiveData()
        AppNews().provideNews().fetchEverything(query, Constants.apiKey, page,  everythingDefaultSize)
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    data?.value = response.body()?.articles }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    data = null }
            })
        return data
    }

    fun getNews(page : Int) : MutableLiveData<MutableList<Articles>>?{
        var dataTop : MutableLiveData<MutableList<Articles>>? = MutableLiveData()
        AppNews().provideNews().getNews(ru, Constants.apiKey, page, everythingDefaultSize)
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    dataTop?.value= response.body()?.articles }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                   dataTop = null }

            })
        return dataTop
    }
}