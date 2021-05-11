package com.yoshitoke.weatheringwithyou.mvp.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City
import com.yoshitoke.weatheringwithyou.utils.network.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


class Model(private val context: Context) : Contract.Model {
    companion object {
        private val apiService by lazy { WeatherAPI.create() }
        private var disposable: Disposable? = null
        private var cityList: List<City> = listOf()
    }

    override fun getWeatherInfo(onWeatherFinishedListener: Contract.Model.OnWeatherFinishedListener, onFetchErrorListener: Contract.Model.OnFetchErrorListener, cityListPosition: Int) {
        disposable = apiService.getWeatherInfo(cityList[cityListPosition].lattitude.toString(), cityList[cityListPosition].longitude.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> onWeatherFinishedListener.onFinished(result)},
                        {error -> onFetchErrorListener.onError(error.message)}
                )
    }

    override fun getCityList(onCityListFinishedListener: Contract.Model.OnCityListFinishedListener, onFetchErrorListener: Contract.Model.OnFetchErrorListener) {
        try {
            val json = loadJSONFromAsset("city_list.json")
            val gson = Gson()
            cityList = gson.fromJson(json , Array<City>::class.java).toList()
            val cityListName: ArrayList<String> = ArrayList()

            for (i in 0 until cityList.size) {
                cityListName.add(cityList[i].name)
            }

            onCityListFinishedListener.onFinished(cityListName)
        }
        catch (e: JSONException) {
            e.printStackTrace()
            onFetchErrorListener.onError(e.toString())
        }
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