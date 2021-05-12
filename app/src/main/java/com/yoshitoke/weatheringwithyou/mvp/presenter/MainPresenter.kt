package com.yoshitoke.weatheringwithyou.mvp.presenter

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo
import com.yoshitoke.weatheringwithyou.utils.AssetsLoader
import com.yoshitoke.weatheringwithyou.utils.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException

class MainPresenter(
        private var mainView: Contract.View?,
        private val context: Context) : Contract.Presenter {

    companion object {
        private var disposable: Disposable? = null
        private var cityList: List<City> = listOf()
    }

    override fun loadCityList() {
        try {
            val json = AssetsLoader.loadJSONFromAsset(context, "city_list.json")
            val gson = Gson()
            cityList = gson.fromJson(json , Array<City>::class.java).toList()
            val cityListName: ArrayList<String> = ArrayList()

            for (i in 0 until cityList.size) {
                cityListName.add(cityList[i].name)
            }

            onCityListResponse(cityListName)
        }
        catch (e: JSONException) {
            e.printStackTrace()
            onError(e.toString())
        }
    }

    private fun onCityListResponse(cityListName: List<String>) {
        mainView?.showSpinnerList(cityListName)
    }

    override fun onLocationSwitched(listPosition: Int) {
        disposable = RetrofitClient.weatherService.getWeatherInfo(cityList[listPosition].lattitude.toString(), cityList[listPosition].longitude.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> onWeatherResponse(result)},
                        {error -> onError(error.message)}
                )
    }

    private fun onWeatherResponse(data: WeatherInfo?) {
        mainView?.showWeatherInfo(data!!)
    }

    private fun onError(error: String?) {
        Log.d("responseErr", error.toString())
    }

    override fun destroy() {
        mainView = null
    }
}
