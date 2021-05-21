package com.yoshitoke.weatheringwithyou.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.R

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = AlarmReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive() called with: context = [$context], intent = [$intent]")
        if (context != null && intent != null && intent.action != null) {
            val json = intent.getStringExtra("alarm")
            val alarm = Gson().fromJson(json, AlarmData::class.java)

            WeatherAlertHelper.checkCurrentWeatherForRegisteredAlarm(alarm, context)
        }
    }
}
