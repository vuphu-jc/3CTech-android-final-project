package com.khaithu.a3ctech_android_final_project.api

import com.khaithu.a3ctech_android_final_project.service.IMovieService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private const val baseUrl : String = "https://api.themoviedb.org/"

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getMovieService() : IMovieService {
        return retrofit().create(IMovieService::class.java)
    }

}
