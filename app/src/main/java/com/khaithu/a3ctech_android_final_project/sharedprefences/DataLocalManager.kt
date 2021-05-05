package com.khaithu.a3ctech_android_final_project.sharedprefences

import android.content.Context
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum

class DataLocalManager {
    private lateinit var mySharedPreference: MySharedPreference

    companion object {
        private lateinit var instance: DataLocalManager


        fun init(context: Context) {
            instance = DataLocalManager()
            instance.mySharedPreference = MySharedPreference(context)
        }

        private fun newInstance(): DataLocalManager {
            if (!this::instance.isInitialized) {
                instance = DataLocalManager()
            }
            return instance
        }

        fun setLanguage(enum : LanguageEnum) {
            newInstance().mySharedPreference.putStringValue(SharePrefKey.LANGUAGE, enum.value)
        }

        fun getLanguage() : String? {
            return newInstance().mySharedPreference.getStringValue(SharePrefKey.LANGUAGE)
        }

        fun setLoginStatus(status: Boolean) {
            newInstance().mySharedPreference.putBooleanValue(SharePrefKey.STATUS_LOGIN, status)
        }

        fun getLoginStatus() : Boolean {
            return newInstance().mySharedPreference.getBooleanValue(SharePrefKey.STATUS_LOGIN)
        }
    }
}
