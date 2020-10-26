package com.example.newskotlin.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class Database  {

      fun getData (context: Context): NewsData{
         return Room.databaseBuilder(
            context,
            NewsData:: class.java,
            "data_name"
        ).build()
    }

}