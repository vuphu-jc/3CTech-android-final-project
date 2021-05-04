package com.khaithu.a3ctech_android_final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.khaithu.a3ctech_android_final_project.view.fragment.HomeView
import com.khaithu.a3ctech_android_final_project.view.fragment.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeView())
        bottomNav.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment = Fragment()
        when (item.itemId) {
            R.id.tabHome -> {
                fragment = HomeView()
                loadFragment(fragment)
                return true
            }
            R.id.tabReview -> {
                fragment = SearchView()
                loadFragment(fragment)
                return true
            }
            R.id.tabUser -> {
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }
}
