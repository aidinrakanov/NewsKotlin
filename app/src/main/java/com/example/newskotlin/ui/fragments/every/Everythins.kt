package com.example.newskotlin.ui.fragments.every

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
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
import org.koin.android.ext.android.inject

class Everythins : BaseFragment<EverythinsViewModel>(R.layout.everythins_fragment), NewsAdapter.OnItemClickListener {

    override val viewModel by inject<EverythinsViewModel>()
    private lateinit var adapterEvery: NewsAdapter
    private var listEvery: MutableList<Articles> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.everythins_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listEvery = mutableListOf()
        adapterEvery = NewsAdapter(listEvery)
        initial()
        subscribeEvery()
        search()
        scroll()
    }

    override fun initial() {
        recycler()

    }

    private fun recycler() {
        main_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterEvery
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun observe() {
        viewModel.fetchEverything("android")
        subscribeEvery()
    }

    private fun subscribeEvery() {
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    adapterEvery.update(listEvery)
                }
            }
        })
    }

    private fun scroll() {
        main_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    override fun onClickListener(article: Articles) {
        DetailsActivity.instanceActivity(this.activity, article)
    }
}