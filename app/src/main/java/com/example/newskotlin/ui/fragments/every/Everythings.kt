package com.example.newskotlin.ui.fragments.every


import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.*
import com.example.newskotlin.extansion.PaginationListener
import com.example.newskotlin.extansion.showToast
import com.example.newskotlin.models.Articles
import com.example.newskotlin.network.Status
import com.example.newskotlin.ui.details.DetailsActivity
import com.example.newskotlin.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.everythins_fragment.*
import kotlinx.android.synthetic.main.item_news.*
import kotlinx.android.synthetic.main.top_fragment.*
import org.koin.android.ext.android.inject


class Everythings : BaseFragment<EverythinsViewModel>(R.layout.everythins_fragment), NewsAdapter.Likelick {

    override val viewModel by inject<EverythinsViewModel>()
    private lateinit var adapterEvery: NewsAdapter
    private var listEvery: MutableList<Articles> = mutableListOf()
    private val linearManager = LinearLayoutManager(activity?.parent)


    override fun initial() {
        search()
        setupScroll()
        recycler()
        detailStart()
        getData()
        swipeListener()
        viewModel.fetchEverything("bitcoin")
    }

    private fun getData() {
        viewModel.getDB.observe(viewLifecycleOwner, Observer {
            adapterEvery.update(it as MutableList<Articles>)
        })
    }


    private fun recycler() {
        adapterEvery = NewsAdapter(listEvery)
        every_recycler.apply {
            layoutManager = LinearLayoutManager(activity?.parent)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = adapterEvery
        }
    }

    private fun swipeListener(){
        swipe.setOnRefreshListener {
            listEvery.clear()
            viewModel.fetchEverything("bitcoin")
            context?.let { showToast(it, "Данные успешно обновлены") }
            swipe.isRefreshing = false
        }
    }

    override fun observe() {
        subscribeEvery()
    }

    private fun subscribeEvery() {
        viewModel.fetchEverything("bitcoin").observe(viewLifecycleOwner, Observer {
            val articles = it.data?.articles
            when (it.status) {
                Status.SUCCESS -> {
                    if (articles != null) {
                        adapterEvery.update(articles)
                    }
                }
            }
        })
    }

    private fun setupScroll() {
        every_recycler?.addOnScrollListener(object : PaginationListener(linearManager) {
            override fun isLastPage(): Boolean {
                return viewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading
            }

            override fun loadMoreItems() {
                viewModel.isLoading = true
                subscribeEvery()
            }
        })
    }


    private fun search() {
        main_search.doAfterTextChanged {
            if (it != null) {
                if (main_search.text.isNotEmpty()) {
                    listEvery.clear()
                    viewModel.fetchEverything(main_search.text.toString())
                    subscribeEvery()
                    adapterEvery.notifyDataSetChanged()
                }
            }
        }
    }

    private fun detailStart() {
        adapterEvery.setOnClick(object : NewsAdapter.OnItemClickListener {
            override fun onClickListener(article: Articles) {
                DetailsActivity.instanceActivity(activity, article)
            }
        })
    }

    override fun onLikeClick(article: Articles) {
        viewModel.insertFav(article)
    }
}
