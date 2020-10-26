package com.example.newskotlin.ui.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.R
import com.example.newskotlin.extansion.loadImage
import com.example.newskotlin.models.Articles
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.*

class NewsAdapter (private var list: MutableList<Articles>):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    private lateinit var onNewsClickListener: OnItemClickListener
    private lateinit var onLikelick: Likelick


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onNewsClickListener.onClickListener(list[position])
        }
    }

    fun update(list: MutableList<Articles>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    fun setOnClick(onItemClickListener: OnItemClickListener) {
        this.onNewsClickListener = onItemClickListener
    }

    fun setOnClick(onLikelick: Likelick) {
        this.onLikelick = onLikelick
    }

    override fun getItemCount(): Int = list.size

    class NewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(article: Articles){
            star.setOnClickListener {
                if (article.isFavorite) {
                    star.setImageResource(R.drawable.ic_star)
                } else {
                    star.setImageResource(R.drawable.ic_star_gold)

                }

            }
            main_title.text = article.title
            main_desc.text = article.description
                if (article.urlToImage !=null){
                    main_image.loadImage(context = main_image.context, url = article.urlToImage)
                }else{
                main_image.setImageResource(R.drawable.no_image)
            }
        }

    }


    interface OnItemClickListener{
        fun onClickListener(article: Articles)
    }

    interface Likelick{
        fun onLikeClick(article: Articles)
    }
}