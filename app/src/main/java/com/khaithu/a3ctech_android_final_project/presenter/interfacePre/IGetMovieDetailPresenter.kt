package com.khaithu.a3ctech_android_final_project.presenter.interfacePre

import com.khaithu.a3ctech_android_final_project.model.MovieDetail

interface IGetMovieDetailPresenter {

    fun getMovieDetail(movieId: Int)

    fun addToWatchlist(movieDetail: MovieDetail)
}
