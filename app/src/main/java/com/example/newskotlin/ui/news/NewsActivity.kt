package com.example.newskotlin.ui.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newskotlin.R
import com.example.newskotlin.ui.fragments.every.Everythings
import com.example.newskotlin.ui.fragments.favorites.Favorites
import com.example.newskotlin.ui.fragments.top.Top
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : AppCompatActivity(){

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val everythins = Everythings()
        val top = Top()
        val favorites = Favorites()

        makeCurrentFragment(everythins)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_top -> makeCurrentFragment(top)
                R.id.menu_every -> makeCurrentFragment(everythins)
                R.id.menu_favorites -> makeCurrentFragment(favorites)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }


}