package com.hieuminh.chatapp.utils

import android.annotation.SuppressLint
import com.hieuminh.chatapp.constant.Constant.TimeFormat.DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toStr() : String {
    return SimpleDateFormat(DATE_FORMAT).format(this)
}
