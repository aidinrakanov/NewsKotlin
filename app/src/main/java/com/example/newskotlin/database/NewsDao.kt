package com.example.newskotlin.database

import androidx.room.*
import com.example.newskotlin.models.Articles


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll (articles: List<Articles>)

    @Insert()
    suspend fun insert (articles: Articles)

    @Query("SELECT * FROM articles")
    suspend fun fetchAllArticles() : List<Articles>

    @Query("SELECT * FROM articles WHERE isFavorite == 1")
    suspend fun fetchFavoriteArticles(): List<Articles>

    @Delete
    suspend fun delete (articles: Articles)

}