package com.example.newskotlin.ui.fragments.top


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.BaseFragment
import com.example.newskotlin.R
import com.example.newskotlin.extansion.PaginationListener
import com.example.newskotlin.models.Articles
import com.example.newskotlin.network.Status
import com.example.newskotlin.ui.details.DetailsActivity
import com.example.newskotlin.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.everythins_fragment.*
import kotlinx.android.synthetic.main.top_fragment.*
import org.koin.android.ext.android.inject

class Top : BaseFragment<TopViewModel>(R.layout.top_fragment) {


    override val viewModel by inject<TopViewModel>()
    private lateinit var adapterTop: NewsAdapter
    private val linearManager = LinearLayoutManager(activity?.parent)
    private var listTop: MutableList<Articles> = mutableListOf()


    override fun initial() {
        recycler()
        detailStart()
        setupScroll()
        getData()
        viewModel.getNews()

    }

    private fun getData() {
        viewModel.getDB.observe(viewLifecycleOwner, Observer {
            adapterTop.update(it as MutableList<Articles>)
        })
    }

    override fun observe() {
        subscribeTop()
    }

    private fun recycler() {
        adapterTop = NewsAdapter(listTop)
        top_recycler.apply {
            layoutManager = LinearLayoutManager(activity?.parent)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = adapterTop

        }
    }

    private fun subscribeTop() {
        viewModel.getNews().observe(viewLifecycleOwner, Observer {
            val articles = it.data?.articles
            when (it.status) {
                Status.SUCCESS -> {
                    if (articles != null) {
                        adapterTop.update(articles)
                    }
                }
            }
        })
    }


    private fun setupScroll() {
        top_recycler?.addOnScrollListener(object : PaginationListener(linearManager) {
            override fun isLastPage(): Boolean {
                return viewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading
            }

            override fun loadMoreItems() {
                viewModel.isLoading = true
                subscribeTop()
            }
        })
    }

    private fun updateAdapter(list: MutableList<Articles>?) {
        list?.let { adapterTop.update(it) }
    }


    private fun detailStart() {
        adapterTop.setOnClick(object : NewsAdapter.OnItemClickListener {
            override fun onClickListener(article: Articles) {
                DetailsActivity.instanceActivity(activity, article)
            }
        })
    }
}