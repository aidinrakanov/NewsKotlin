package com.example.newskotlin.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Source(
    var id: String?,
    var name: String?
) : Serializable

@Entity
data class Articles(
    var source: Source?,
    var author: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "url")
    var url: String?,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String?,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String?,
    var content: String?,
    var isFavorite: Boolean = false
) : Serializable