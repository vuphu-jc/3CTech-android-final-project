package com.withYoshitoke.weathering.network

import com.withYoshitoke.weathering.features.weather_info_show.model.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(@Query("id") cityId: Int): Call<WeatherInfoResponse>

    @GET("onecall")
    fun callApiForWeatherForecast(@Query("lat") latitude: Double, @Query("long") longitude: Double): Call<WeatherInfoResponse>
}