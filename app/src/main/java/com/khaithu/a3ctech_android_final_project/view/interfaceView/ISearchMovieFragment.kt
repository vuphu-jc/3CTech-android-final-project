package com.khaithu.a3ctech_android_final_project.view.interfaceView

import com.khaithu.a3ctech_android_final_project.model.ResultMovie

interface ISearchMovieFragment {
    fun onSearchSuccess(results : List<ResultMovie>, totalPage : Int)
}
