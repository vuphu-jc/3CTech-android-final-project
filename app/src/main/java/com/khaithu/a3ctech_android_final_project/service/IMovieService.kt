package com.khaithu.a3ctech_android_final_project.service

import com.khaithu.a3ctech_android_final_project.model.PageMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieService {

    @GET("3/movie/{category}")
    fun listOfMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Call<PageMovie>
}
