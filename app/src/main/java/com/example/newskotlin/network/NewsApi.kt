package com.example.newskotlin.network

import com.example.newskotlin.models.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    suspend fun fetchEverything(
        @Query("q") q: String?,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): ResponseBody

    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("items") items: Int
    ): ResponseBody
}