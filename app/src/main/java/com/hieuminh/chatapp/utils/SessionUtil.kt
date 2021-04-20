@file:Suppress("DEPRECATION")

package com.hieuminh.chatapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.hieuminh.chatapp.constant.Constant

class SessionUtil(context: Context) {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var userId: String?
        set(value) = prefs.edit().putString(Constant.USER_ID, value).apply()
        get() = prefs.getString(Constant.USER_ID, "")
}
