package com.khaithu.a3ctech_android_final_project.presenter

import android.view.View
import com.khaithu.a3ctech_android_final_project.api.RestClient
import com.khaithu.a3ctech_android_final_project.model.PageMovie
import com.khaithu.a3ctech_android_final_project.model.MovieRequest
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.IGetMoviePresenter
import com.khaithu.a3ctech_android_final_project.service.IMovieService
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IHomeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class GetMoviePresenter(val iHomePageFragment: IHomeView) : IGetMoviePresenter {

    lateinit var movieService: IMovieService
    private val page: Int = 5
    private val apiKey: String = "0e47cd8143d6b9a833d0258baff7d491"
    private val category: String = "popular"


    override fun getMovie() {
        val movieRequest: MovieRequest = MovieRequest(page, apiKey, LanguageEnum.VIETNAM, category)

        movieService = RestClient.getMovieService()

        val call: Call<PageMovie> = movieService.listOfMovies(
            movieRequest.category,
            movieRequest.apiKey,
            movieRequest.language.value,
            movieRequest.page
        )
        call.enqueue(object : Callback<PageMovie> {
            override fun onResponse(call: Call<PageMovie>, response: Response<PageMovie>) {
                val pageMovie: PageMovie? = response.body()
                if (pageMovie != null) {
                    iHomePageFragment.onMoviesReceived(pageMovie.result)
                }
            }

            override fun onFailure(call: Call<PageMovie>, t: Throwable) {
            }
        })
    }
}

