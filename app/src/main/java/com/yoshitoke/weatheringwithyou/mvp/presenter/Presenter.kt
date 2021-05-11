package com.yoshitoke.weatheringwithyou.mvp.presenter

import android.util.Log
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo

class Presenter(
        private var mainView: Contract.View,
        private val model: Contract.Model) : Contract.Presenter,
        Contract.Model.OnWeatherFinishedListener, Contract.Model.OnFetchErrorListener, Contract.Model.OnCityListFinishedListener {

    override fun loadCityList() {
        model.getCityList(this, this)
    }

    override fun onLocationSwitched(listPosition: Int) {
        model.getWeatherInfo(this, this, listPosition)
    }

    override fun onFinished(cityListName: List<String>) {
        mainView.showSpinnerList(cityListName)
    }

    override fun onFinished(data: WeatherInfo?) {
        mainView.showWeatherInfo(data!!)
    }

    override fun onError(error: String?) {
    }

//    override fun onDestroy() {
//        mainView = null
//    }

}
