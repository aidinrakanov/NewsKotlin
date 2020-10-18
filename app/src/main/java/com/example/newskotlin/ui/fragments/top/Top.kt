package com.example.newskotlin.ui.fragments.top


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.BaseFragment
import com.example.newskotlin.R
import com.example.newskotlin.models.Articles
import com.example.newskotlin.network.Status
import com.example.newskotlin.ui.details.DetailsActivity
import com.example.newskotlin.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.top_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class Top : BaseFragment<TopViewModel>(R.layout.top_fragment){


    override val viewModel by viewModel<TopViewModel>()
    private lateinit var adapterTop: NewsAdapter
    private var listTop: MutableList<Articles> = mutableListOf()


    override fun initial() {
        recycler()
        detailStart()
        scrollTop()
    }

    override fun observe() {
        subscribeTop()
    }
    private fun recycler() {
        adapterTop = NewsAdapter(listTop)
        top_recycler.apply {
            layoutManager = LinearLayoutManager(this@Top.context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = adapterTop

        }
    }

    private fun subscribeTop() {
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    adapterTop.update(listTop)
                }
            }
        })
    }
    private fun scrollTop() {
        top_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.isPagination.value = true
                    viewModel.getNews()
                }
            }
        })
    }


    private fun detailStart() {
        adapterTop.setOnClick(object : NewsAdapter.OnItemClickListener{
            override fun onClickListener(article: Articles) {
                DetailsActivity.instanceActivity(this@Top.activity, article)
            }
        })
    }

}