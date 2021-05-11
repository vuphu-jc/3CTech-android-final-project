package com.yoshitoke.weatheringwithyou.mvp.presenter

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo
import com.yoshitoke.weatheringwithyou.mvp.model.Model
import com.yoshitoke.weatheringwithyou.utils.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

class MainPresenter(
        private var mainView: Contract.View?,
        private val model: Contract.Model,
        private val context: Context) : Contract.Presenter,
        Contract.Model.OnWeatherFinishedListener, Contract.Model.OnFetchErrorListener, Contract.Model.OnCityListFinishedListener {

    companion object {
        private var disposable: Disposable? = null
        private var cityList: List<City> = listOf()
    }

    override fun loadCityList() {
        try {
            val json = loadJSONFromAsset("city_list.json")
            val gson = Gson()
            cityList = gson.fromJson(json , Array<City>::class.java).toList()
            val cityListName: ArrayList<String> = ArrayList()

            for (i in 0 until cityList.size) {
                cityListName.add(cityList[i].name)
            }

            onFinished(cityListName)
        }
        catch (e: JSONException) {
            e.printStackTrace()
            onError(e.toString())
        }
    }

    override fun onFinished(cityListName: List<String>) {
        mainView?.showSpinnerList(cityListName)
    }

    override fun onLocationSwitched(listPosition: Int) {
        disposable = RetrofitClient.weatherService.getWeatherInfo(cityList[listPosition].lattitude.toString(), cityList[listPosition].longitude.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> onFinished(result)},
                        {error -> onError(error.message)}
                )
    }

    override fun onFinished(data: WeatherInfo?) {
        mainView?.showWeatherInfo(data!!)
    }

    override fun onError(error: String?) {
    }

    override fun destroyView() {
        mainView = null
    }

    private fun loadJSONFromAsset(fileName: String): String {
        val json: String?
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}
