package com.example.newskotlin.ui.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newskotlin.R
import com.example.newskotlin.models.Articles
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_news.*

class NewsAdapter (private var list: MutableList<Articles>,
                  private val onNewsClickListener: OnItemClickListener):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.list_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onNewsClickListener.onClickListener(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    class NewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(article: Articles){
            main_title.text = article.title
            main_desc.text = article.description
            if (article.urlToImage != null){
                Glide.with(main_image.context).load(article.urlToImage).into(main_image)
            } else{
                main_image.setImageResource(R.drawable.no_image)
            }
        }
    }

    interface OnItemClickListener{
        fun onClickListener(article: Articles)
    }
}