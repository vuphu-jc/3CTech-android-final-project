package com.khaithu.a3ctech_android_final_project.view.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.adapter.HomePageRecyclerAdapter
import com.khaithu.a3ctech_android_final_project.adapter.SliderAdapter
import com.khaithu.a3ctech_android_final_project.model.ResultMovie
import com.khaithu.a3ctech_android_final_project.model.SliderData
import com.khaithu.a3ctech_android_final_project.presenter.GetMoviePresenter
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IHomeView
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_homepage.view.*

class HomeView : Fragment(), IHomeView {

    var getMoviePresenter: GetMoviePresenter = GetMoviePresenter(this)
    lateinit var mView: View
    var homeAdapter : HomePageRecyclerAdapter = HomePageRecyclerAdapter()

    var sliderItems: List<SliderData> = arrayListOf(
        SliderData("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/5NxjLfs7Bi07bfZCRl9CCnUw7AA.jpg"),
        SliderData("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/57vVjteucIF3bGnZj6PmaoJRScw.jpg"),
        SliderData("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/kf456ZqeC45XTvo6W9pW5clYKfQ.jpg")
    )
    lateinit var sliderAdapter: SliderAdapter

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_homepage, container, false)
        getMoviePresenter.getMovie()
        createRecyclerView(mView)
        createSlider(mView)
        return mView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun createSlider(view: View) {
        view.slider.apply {
            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            setSliderAdapter(SliderAdapter(context, sliderItems))
            scrollTimeInSec = 4
            isAutoCycle = true
            startAutoCycle()
        }
    }

    override fun onMoviesReceived(movies: List<ResultMovie>) {
        homeAdapter.updateData(movies)
    }

    private fun createRecyclerView(view: View) {
        homeAdapter = HomePageRecyclerAdapter()
        view.recyclerView.adapter =  homeAdapter
        view.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}
