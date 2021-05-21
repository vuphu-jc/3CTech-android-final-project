package com.yoshitoke.weatheringwithyou.alarm

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AlarmData(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("weather_type")
        var weatherTypes: List<String>? = null,
        @SerializedName("hour")
        var hour: Int? = 0,
        @SerializedName("minute")
        var minute: Int? = 0,
        @SerializedName("days")
        var days: List<String>? = null,
        @SerializedName("administered")
        var administered: Boolean = false
)
