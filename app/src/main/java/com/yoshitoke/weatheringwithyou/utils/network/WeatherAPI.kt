package com.yoshitoke.weatheringwithyou.utils.network

import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val APP_ID = "9c1bdf56a58934c804d690c1d30f7714"
const val EXCLUDE_PARAMS = "daily"
const val GET_ONECALL_REQUEST = "onecall?exclude=$EXCLUDE_PARAMS&appid=$APP_ID"

const val ICON_URL_PREFIX = "http://openweathermap.org/img/wn/"
const val ICON_URL_POSTFIX = "@2x.png"

interface WeatherAPI {
    @GET(GET_ONECALL_REQUEST)
    fun getWeatherInfo(@Query("lat") cityLon : String, @Query("lon") cityLat : String) : Observable<WeatherInfo>
}
