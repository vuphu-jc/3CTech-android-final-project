package com.khaithu.a3ctech_android_final_project.view.interfaceView

import android.view.View
import com.khaithu.a3ctech_android_final_project.model.ResultMovie

interface IHomeView {
    fun onMoviesReceived(movies:  List<ResultMovie>)
}
