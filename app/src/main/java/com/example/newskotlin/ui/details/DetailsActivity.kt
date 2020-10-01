package com.example.newskotlin.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.newskotlin.R
import com.example.newskotlin.models.Articles
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Details"
        getData()
    }

    private fun getData() {
        if (intent != null) {
            var articles = intent.getSerializableExtra("send") as Articles
            detail_title.text = articles.title
            detail_desc.text = articles.description
            detail_date.text = articles.publishedAt
            if (articles.urlToImage != null) {
                Glide.with(this).load(articles.urlToImage).into(detail_image)
            }else{
                detail_image.setImageResource(R.drawable.no_image)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

}