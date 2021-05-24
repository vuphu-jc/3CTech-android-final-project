package com.yoshitoke.weatheringwithyou.mvp.model.DataClass

import com.google.gson.annotations.SerializedName


data class WeatherInfo(
    @SerializedName("current")
    val current: Current,
    @SerializedName("hourly")
    val hours: List<Hourly> = listOf(),
    @SerializedName("daily")
    val days: List<Daily> = listOf()
)

data class Current(
    @SerializedName("dt")
    val dateTime: Int,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("weather")
    val weathers: List<Weather> = listOf(),
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("feels_like")
    val temp_feels_like: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("uvi")
    val uvIndex: Double
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

data class Daily(
    @SerializedName("dt")
    val dateTime: Int,
    @SerializedName("temp")
    val temperature: DailyTemp,
    @SerializedName("weather")
    val weathers: List<Weather> = listOf()
)

data class DailyTemp(
    @SerializedName("min")
    val min: Double,
    @SerializedName("max")
    val max: Double
)
