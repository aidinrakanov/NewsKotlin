package com.example.newskotlin.network

import com.example.newskotlin.models.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    fun fetchEverything(
        @Query("q") q: String?,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): Call<ResponseBody>

    @GET("/v2/top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("items") items: Int
    ): Call<ResponseBody>
}