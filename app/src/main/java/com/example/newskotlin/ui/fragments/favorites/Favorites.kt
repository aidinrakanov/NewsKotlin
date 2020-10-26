package com.example.newskotlin.ui.fragments.favorites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newskotlin.BaseFragment
import com.example.newskotlin.R
import com.example.newskotlin.ui.fragments.every.EverythinsViewModel
import org.koin.android.ext.android.inject

class Favorites : BaseFragment<FavoritesViewModel>(R.layout.favorites_fragment) {

    override val viewModel by inject<FavoritesViewModel>()
    override fun initial() {

    }

    override fun observe() {

    }
}