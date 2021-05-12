package com.yoshitoke.weatheringwithyou.mvp.model.DataClass

import com.google.gson.annotations.SerializedName


data class WeatherInfo(
    @SerializedName("current")
    val current: Current,
    @SerializedName("hourly")
    val hours: List<Hourly> = listOf()
)

data class Current(
    @SerializedName("dt")
    val dateTime: Int,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("weather")
    val weathers: List<Weather> = listOf()
)

data class Weather(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val iconName: String
)

data class Hourly(
    @SerializedName("dt")
    val dateTime: Int,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("weather")
    val weathers: List<Weather> = listOf()
)
