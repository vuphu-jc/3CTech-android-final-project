package com.khaithu.a3ctech_android_final_project.sharedprefences

import android.content.Context
import android.content.SharedPreferences

class MySharedPreference(var context: Context) {
    private val MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE"

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        MY_SHARED_PREFERENCE,
        Context.MODE_PRIVATE
    )

    fun putStringValue(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        return sharedPreference.getString(key, "")
    }
}
