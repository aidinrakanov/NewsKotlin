package com.example.newskotlin.ui.details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.newskotlin.R
import com.example.newskotlin.extansion.loadImage
import com.example.newskotlin.models.Articles
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_news.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Details"
        getData()
    }

    private fun getData() {

            detail_title.text = articles?.title
            detail_desc.text = articles?.description
            detail_date.text = articles?.publishedAt
            detail_image.loadImage(this, articles?.urlToImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }


    companion object {
        private var articles: Articles? = null
        fun instanceActivity(activity: Activity?, articles: Articles) {
            this.articles = articles
            val intent = Intent(activity, DetailsActivity::class.java)
            activity?.startActivity(intent)
        }
    }

}