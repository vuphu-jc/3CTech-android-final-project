package com.withYoshitoke.weathering.features.weather_info_show.presenter

import android.view.View
import com.withYoshitoke.weathering.features.weather_info_show.model.WeatherInfoShowModel
import com.withYoshitoke.weathering.common.RequestCompleteListener
import com.withYoshitoke.weathering.features.weather_info_show.model.data_class.City
import com.withYoshitoke.weathering.features.weather_info_show.model.data_class.WeatherDataModel
import com.withYoshitoke.weathering.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.withYoshitoke.weathering.features.weather_info_show.view.MainActivityView
import com.withYoshitoke.weathering.utils.kelvinToCelsius
import com.withYoshitoke.weathering.utils.unixTimestampToDateTimeString
import com.withYoshitoke.weathering.utils.unixTimestampToTimeString

class WeatherInfoShowPresenterImpl(
        private var view: MainActivityView?,
        private val model: WeatherInfoShowModel) : WeatherInfoShowPresenter {

    override fun fetchCityList() {
        model.getCityList(object : RequestCompleteListener<MutableList<City>> {

            override fun onRequestSuccess(data: MutableList<City>) {
                view?.onCityListFetchSuccess(data) //let view know the formatted city list data
            }

            override fun onRequestFailed(errorMessage: String) {
                view?.onCityListFetchFailure(errorMessage) //let view know about failure
            }
        })
    }

    override fun fetchWeatherInfo(cityId: Int) {

        view?.handleProgressBarVisibility(View.VISIBLE) // let view know about progress bar visibility

        model.getWeatherInformation(cityId, object : RequestCompleteListener<WeatherInfoResponse> {

            override fun onRequestSuccess(data: WeatherInfoResponse) {

                view?.handleProgressBarVisibility(View.GONE) // let view know about progress bar visibility

                val weatherDataModel = WeatherDataModel(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                view?.onWeatherInfoFetchSuccess(weatherDataModel) //let view know the formatted weather data
            }

            override fun onRequestFailed(errorMessage: String) {
                view?.handleProgressBarVisibility(View.GONE)

                view?.onWeatherInfoFetchFailure(errorMessage) //let view know about failure
            }
        })
    }

    override fun detachView() {
        view = null
    }
}