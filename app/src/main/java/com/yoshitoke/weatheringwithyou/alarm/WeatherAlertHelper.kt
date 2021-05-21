package com.yoshitoke.weatheringwithyou.alarm

import android.content.Context
import android.util.Log
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo
import com.yoshitoke.weatheringwithyou.utils.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object WeatherAlertHelper {
    var bruneiTest: City = City("Brunei",  114.949008710682, 4.8977738654898)
    fun checkCurrentWeatherForRegisteredAlarm(alarmData: AlarmData, context: Context) {
        val disposable = RetrofitClient.weatherService.getWeatherInfo(bruneiTest.latitude.toString(), bruneiTest.longitude.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result ->
                            val currentWeather = result.current.weathers[0].main
                            val list = alarmData.weatherTypes as List<String>
                            Log.d("weatherAlert: ", list.toString())

                            if (list.contains(currentWeather)) {
                                NotificationHelper.createSampleDataNotification(context, "Alert", "fuck", "Current Weather Outside: $currentWeather", false)
                            }
                        },
                        {error -> Log.d("api error", error.message.toString())}
                )
    }
}
