package com.example.newskotlin.ui.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newskotlin.R
import com.example.newskotlin.ui.fragments.every.Everythins
import com.example.newskotlin.ui.fragments.top.Top
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : AppCompatActivity(){

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val everythins = Everythins()
        val top = Top()

        makeCurrentFragment(top)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_top -> makeCurrentFragment(top)
                R.id.menu_every -> makeCurrentFragment(everythins)
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