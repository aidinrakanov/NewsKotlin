package com.example.newskotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newskotlin.models.Articles


@Database(entities = [Articles::class], version = 1)
abstract class NewsData : RoomDatabase(){
    abstract fun newsDao(): NewsDao
}