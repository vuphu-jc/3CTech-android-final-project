package com.khaithu.a3ctech_android_final_project.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
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
import com.khaithu.a3ctech_android_final_project.adapter.interfaceadapter.IHandleEvent
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.ResultMovie
import com.khaithu.a3ctech_android_final_project.model.SliderData
import com.khaithu.a3ctech_android_final_project.presenter.GetMoviePresenter
import com.khaithu.a3ctech_android_final_project.view.MovieDetailActivity
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IHomeView
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_homepage.view.*

class HomeView : Fragment(), IHomeView, IHandleEvent {

    private var mPresenter: GetMoviePresenter = GetMoviePresenter(this)

    private lateinit var mView: View
    private lateinit var mHomeAdapter: HomePageRecyclerAdapter

    //TODO Upgrade fetch data from server
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
        mPresenter.getMovies()
        createRecyclerView(mView)
        createSlider(mView)
        return mView
    }

    override fun onMoviesReceived(movies: List<ResultMovie>) {
        mHomeAdapter.updateData(movies)
    }

    override fun onClickEvent(id: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(Constant.intentDetailMovie, id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroyPresenter()
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

    private fun createRecyclerView(view: View) {
        mHomeAdapter = HomePageRecyclerAdapter(this)
        view.recyclerView.adapter = mHomeAdapter
        view.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }



}
