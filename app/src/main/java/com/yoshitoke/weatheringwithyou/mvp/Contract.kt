package com.yoshitoke.weatheringwithyou.mvp

import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo

interface Contract {
    interface View {

        fun showSpinnerList(data: List<String>)

        fun showWeatherInfo(data: WeatherInfo)

    }

    interface Model {

        interface OnWeatherFinishedListener {
            fun onFinished(data: WeatherInfo?)
        }

        interface OnCityListFinishedListener {
            fun onFinished(cityListName: List<String>)
        }

        interface OnFetchErrorListener {
            fun onError(error: String?)
        }

        fun getCityList(onCityListFinishedListener: OnCityListFinishedListener, onFetchErrorListener: OnFetchErrorListener)

        fun getWeatherInfo(onWeatherFinishedListener: OnWeatherFinishedListener, onFetchErrorListener: OnFetchErrorListener, cityListPosition: Int)

    }

    interface Presenter {

        fun loadCityList()

        fun onLocationSwitched(listPosition: Int)

    }
}
