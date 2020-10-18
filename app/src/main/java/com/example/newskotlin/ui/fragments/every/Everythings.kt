package com.example.newskotlin.ui.fragments.every


import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.*
import com.example.newskotlin.models.Articles
import com.example.newskotlin.network.Status
import com.example.newskotlin.ui.details.DetailsActivity
import com.example.newskotlin.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.everythins_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class Everythings: BaseFragment<EverythinsViewModel>(R.layout.everythins_fragment) {

    override val viewModel by viewModel <EverythinsViewModel>()
    private lateinit var adapterEvery: NewsAdapter
    private var listEvery: MutableList<Articles> = mutableListOf()


    override fun initial() {
        search()
        scroll()
        recycler()
        detailStart()
    }


    private fun recycler() {
        adapterEvery = NewsAdapter(listEvery)
        every_recycler.apply {
            layoutManager = LinearLayoutManager(this@Everythings.context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = adapterEvery
        }
    }

    override fun observe() {
        viewModel.fetchEverything("android")
        subscribeEvery()
    }

    private fun subscribeEvery() {
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    adapterEvery.update(listEvery)
                }
            }
        })
    }

    private fun scroll() {
        every_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.isPagination.value = true
                    viewModel.fetchEverything("android")
                }
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
        adapterEvery.setOnClick(object : NewsAdapter.OnItemClickListener{
            override fun onClickListener(article: Articles) {
                DetailsActivity.instanceActivity(this@Everythings.activity, article)
            }
        })
    }
}
