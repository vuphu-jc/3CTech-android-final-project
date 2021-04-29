package com.withYoshitoke.weathering.features.weather_info_show.model

import com.withYoshitoke.weathering.common.RequestCompleteListener
import com.withYoshitoke.weathering.features.weather_info_show.model.data_class.City
import com.withYoshitoke.weathering.features.weather_info_show.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInformation(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}