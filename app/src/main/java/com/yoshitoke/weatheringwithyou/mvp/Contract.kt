package com.yoshitoke.weatheringwithyou.mvp

import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo

interface Contract {
    interface View {

        fun showSpinnerList(data: List<String>)

        fun showWeatherInfo(data: WeatherInfo)

    }

    interface BasePresenter {
        fun destroy()
    }

    interface Presenter: BasePresenter {

        fun loadCityList()

        fun onLocationSwitched(listPosition: Int)

    }
}

