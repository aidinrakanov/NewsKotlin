package com.example.newskotlin.extansion

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newskotlin.R


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun ImageView.loadImage(context: Context, url: String?, placeholder: Int = 0) {
    Glide.with(context)
        .asBitmap()
        .placeholder(placeholder)
        .load(url)
        .into(this)
}