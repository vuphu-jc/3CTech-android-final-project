package com.khaithu.a3ctech_android_final_project.service

import com.khaithu.a3ctech_android_final_project.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieService {

    @GET("3/movie/{category}")
    fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int
    ): Observable<PageMovie>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Observable<MovieDetail>

    @GET("3/movie/{movie_id}/videos")
    fun getVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Observable<Video>

    @GET("3/search/{category}")
    fun search(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query(value = "include_adult") adult: Boolean
    ): Observable<Search>
}
