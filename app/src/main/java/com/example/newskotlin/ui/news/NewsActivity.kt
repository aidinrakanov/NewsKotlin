package com.example.newskotlin.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newskotlin.AppNews
import com.example.newskotlin.R
import com.example.newskotlin.extansion.showToast
import com.example.newskotlin.models.Articles
import com.example.newskotlin.ui.details.DetailsActivity
import com.example.newskotlin.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {

    private lateinit var list: MutableList<Articles>
    private lateinit var mViewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private var flag: Boolean? = true
    private var isRequest: Boolean? = true
    private var page = 0
    private var pageIems = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        recyclerSets()
        mViewModel.getNews(page)
        subscribeToNews()
        search()
        scrollNews()
    }

    private fun scrollNews() {
        main_scroll.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if (mViewModel.articles.value!!.size >= 10) {
                        page++
                        main_progress.visibility = View.VISIBLE
                        if (flag!!) {
                            mViewModel.fetchEverything("kotlin")
                        } else {
                            mViewModel.getNews(page)
                        }
                        subscribeToNews()
                    }
                }
            })
    }

    private fun search() {
        main_search.doAfterTextChanged {
            if (it != null) {
                if (main_search.text.isNotEmpty()) {
                    list.clear()
                    mViewModel.fetchEverything(main_search.text.toString())
                    subscribeToNews()
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun recyclerSets() {
        main_recycler.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = this@NewsActivity.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initViews() {
        mViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        list = mutableListOf()
        adapter = NewsAdapter(list, this)
    }


//    private fun subscribeToTopHeadlines() {
//        mViewModel.articles.observe(this, Observer {
//            mViewModel.getNews(page)
//            list.addAll(it)
//            adapter.notifyDataSetChanged()
//            main_progress.visibility = View.GONE
//        })
//    }
    private fun subscribeToNews() {
        mViewModel.articles.observe(this, Observer {
            list.addAll(it)
            adapter.notifyDataSetChanged()
            main_progress.visibility = View.GONE
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_change_news) {
            flag = if (flag!!) {
                showToast(this, "Every")
                main_search.visibility = View.VISIBLE
                list.clear()
                mViewModel.fetchEverything("kotlin")
                subscribeToNews()
                item.setIcon(R.drawable.ic_newss)
                page = 0
                isRequest = true
                false
            } else {
                showToast(this, "Top")
                main_search.visibility = View.GONE
                list.clear()
                mViewModel.getNews(page)
                item.setIcon(R.drawable.ic_get)
                subscribeToNews()
                isRequest = false
                page = 0
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickListener(article: Articles) {
        DetailsActivity.instanceActivity(this, article)
    }


}