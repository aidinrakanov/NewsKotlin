package com.example.newskotlin.database.converter

import androidx.room.TypeConverter
import com.example.newskotlin.models.Articles
import com.example.newskotlin.models.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class converterJson {

    @TypeConverter
    fun toRaw(source: Source?): String? {
        if (source == null) return null
        val gson = Gson()
        val type = object : TypeToken<Source>(){}.type
        return gson.toJson(source, type)
    }

    @TypeConverter
    fun fromRaw(raw: String?): Source? {
        if (raw == null) return null
        val gson = Gson()
        val type = object : TypeToken<Source>() {}.type
        return gson.fromJson<Source>(raw, type)
    }
}