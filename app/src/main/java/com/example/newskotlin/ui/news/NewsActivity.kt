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
    private var page = 1
    private var pageIems = 10


    private lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        mViewModel.fetchEverything("bitcoin", pageIems)
        recyclerSets()
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
                        if (isRequest!!) {
                            mViewModel.fetchEverything("bitcoin", page)
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
                    mViewModel.fetchEverything(main_search.text.toString(), pageIems)
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

    private fun subscribeToNews() {
        mViewModel.articles.observe(this, Observer {
            list.addAll(it)
            adapter.notifyDataSetChanged()
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
                showToast(this, "Top_headlines")
                list.clear()
                page = 1
                mViewModel.getNews(page)
                subscribeToNews()
                item.setIcon(R.drawable.ic_newss)
                isRequest = false
                false
            } else {
                showToast(this, "Everythings")
                list.clear()
                mViewModel.fetchEverything("bitcoin", pageIems)
                item.setIcon(R.drawable.ic_get)
                subscribeToNews()
                isRequest = true
                page = 1
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickListener(article: Articles) {
        startActivity(
            Intent(this, DetailsActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("send", article)
        )
    }
}