package com.example.newskotlin.repository


import androidx.lifecycle.liveData
import com.example.newskotlin.Constants
import com.example.newskotlin.database.NewsData
import com.example.newskotlin.models.Articles
import com.example.newskotlin.network.NewsApi
import com.example.newskotlin.network.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsRepository(val newsApi: NewsApi, val newsData: NewsData) {

    private val defaultSize = 10
    private val ru = "ru"

   fun fetchEverythings(query: String?, page : Int) = liveData(Dispatchers.IO) {
       emit(Resource.loading(data = null))
       try {
           val result = newsApi.fetchEverything(query, Constants.apiKey, defaultSize ,page )
           newsData.newsDao().insertAll(result.articles)
           emit(Resource.success(data = result))
       }catch (ex : Exception){
           emit(Resource.error(data = null, message = ex.message ?: "Error every"))
       }
   }

   fun getTopHeadlines() = liveData(Dispatchers.IO) {
       emit(Resource.loading(data = null))
       try {
           emit(Resource.success(data = newsApi.getNews(ru, Constants.apiKey, defaultSize, items = 1)))
       }catch (ex : Exception){
           emit(Resource.error(data = null, message = ex.message ?: "Error top"))
       }
   }

    //room

    fun insertNewNews(article: Articles) {
        CoroutineScope(Dispatchers.IO).launch {
            article.isFavorite = true
            newsData.newsDao().insert(article)
        }
    }

    fun deleteFav(article: Articles){
        CoroutineScope(Dispatchers.IO).launch {
            article.isFavorite = false
            newsData.newsDao().delete(article)
        }
    }

    fun fetchFavorites() = liveData(Dispatchers.IO) {
        emit(newsData.newsDao().fetchFavoriteArticles())
    }

}