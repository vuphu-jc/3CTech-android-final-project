package com.yoshitoke.weatheringwithyou.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
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

fun Int.toPercentageString() : String {
    return "$this %"
}

fun Double.toVelocityString() : String {
    return "$this m/s"
}

fun String.toFirstCapString() : String {
    return this.substring(0, 1).toUpperCase(Locale.ROOT) + this.substring(1)
}

fun Context.showConfirmDialog(title: String, message: String, actionIfAgree: () -> Unit) {
    val alertDialog = AlertDialog.Builder(this).create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
        dialog.dismiss()
    }
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { dialog, _ ->
        actionIfAgree()
        dialog.dismiss()
    }
    alertDialog.show()
}

fun Activity.checkAndRequestPermission(
        title: String, message: String,
        manifestPermission: String, requestCode: Int,
        action: () -> Unit
) {
    val permissionStatus = ContextCompat.checkSelfPermission(applicationContext, manifestPermission)

    if (permissionStatus == PackageManager.PERMISSION_DENIED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, manifestPermission)) {
            applicationContext.showConfirmDialog(title, message) {
                requestPermission(manifestPermission, requestCode)
            }
        } else {
            // No explanation needed -> request the permission
            requestPermission(manifestPermission, requestCode)
        }
    } else {
        action()
    }
}

fun Activity.requestPermission(manifestPermission: String, requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(manifestPermission), requestCode)
}