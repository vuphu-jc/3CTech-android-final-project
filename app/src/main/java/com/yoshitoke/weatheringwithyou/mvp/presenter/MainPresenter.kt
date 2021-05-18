package com.yoshitoke.weatheringwithyou.mvp.presenter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.CityDatabaseHandler
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
        private lateinit var cityList: List<City>
        private lateinit var weatherInfo: WeatherInfo
    }

    override fun loadCityList() {
        val cityDB = CityDatabaseHandler(context)
        cityList = cityDB.FetchCities("%") // fetch all

        if (cityList.isEmpty()) {
            try {
                val json = AssetsLoader.loadJSONFromAsset(context, "city_list.json")
                val gson = Gson()
                cityList = gson.fromJson(json , Array<City>::class.java).toList()

                cityList.forEach{
                    val values = ContentValues()
                    values.put("name", it.name)
                    values.put("latitude", it.latitude)
                    values.put("longitude", it.longitude)

                    cityDB.AddCity(values)
                }
            }
            catch (e: JSONException) {
                e.printStackTrace()
                onError(e.toString())
            }
        }

        onCityListResponse(cityList)
    }

    override fun switchLocation(listPosition: Int) {
        disposable = RetrofitClient.weatherService.getWeatherInfo(cityList[listPosition].latitude.toString(), cityList[listPosition].longitude.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> onWeatherResponse(result)},
                        {error -> onError(error.message)}
                )
    }

    override fun getWeatherLocalData(): WeatherInfo? {
        return weatherInfo
    }

    override fun destroy() {
        mainView = null
        disposable?.dispose()
    }

    private fun onCityListResponse(cityList: List<City>) {
        mainView?.showSpinnerList(cityList)
    }

    private fun onWeatherResponse(data: WeatherInfo?) {
        mainView?.showWeatherInfo(data!!)
        weatherInfo = data!!
    }

    private fun onError(error: String?) {
        Log.d("responseErr", error.toString())
    }
}
