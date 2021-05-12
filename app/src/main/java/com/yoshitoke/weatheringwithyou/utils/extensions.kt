package com.yoshitoke.weatheringwithyou.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Int.unixTimestampToString(format: String) : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}


fun Double.kelvinToCelsius() : Int {
    return (this - 273.15).toInt()
}

fun Int.toCelsiusString() : String {
    return "$this°C"
}