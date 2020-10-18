package com.example.newskotlin.ui.fragments.top

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.BaseFragment
import com.example.newskotlin.R
import com.example.newskotlin.models.Articles
import com.example.newskotlin.network.Status
import com.example.newskotlin.ui.details.DetailsActivity
import com.example.newskotlin.ui.fragments.every.EverythinsViewModel
import com.example.newskotlin.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.everythins_fragment.*
import org.koin.android.ext.android.inject

class Top : BaseFragment<TopViewModel>(R.layout.top_fragment), NewsAdapter.OnItemClickListener {


    override val viewModel by inject<TopViewModel>()
    private lateinit var adapterTop: NewsAdapter
    private var listTop: MutableList<Articles> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listTop = mutableListOf()
        adapterTop = NewsAdapter(listTop)
        initial()
        subscribeTop()
        scrollTop()
    }

    override fun initial() {
        recycler()
    }

    override fun observe() {

    }
    private fun recycler() {
        main_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterTop
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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
        main_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.isPagination.value = true
                    viewModel.getNews()
                }
            }
        })
    }


    override fun onClickListener(article: Articles) {
        DetailsActivity.instanceActivity(this.activity, article)
    }

}