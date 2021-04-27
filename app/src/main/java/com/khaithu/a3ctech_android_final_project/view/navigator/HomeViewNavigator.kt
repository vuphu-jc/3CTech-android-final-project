package com.khaithu.a3ctech_android_final_project.view.navigator

import android.content.Intent
import androidx.core.content.ContextCompat
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.view.MovieDetailActivity
import com.khaithu.a3ctech_android_final_project.view.fragment.HomeView
import com.khaithu.a3ctech_android_final_project.view.navigator.navigatorinterface.IHomeViewNavigator

class HomeViewNavigator(var homeView: HomeView) : IHomeViewNavigator {

    override fun goToMovieDetailView(value: Int) {
        val intent: Intent = Intent(homeView.context, MovieDetailActivity::class.java)
        intent.putExtra(Constant.intentDetailMovie, value)
        homeView.context?.let { ContextCompat.startActivity(it, intent, null) }
    }
}
