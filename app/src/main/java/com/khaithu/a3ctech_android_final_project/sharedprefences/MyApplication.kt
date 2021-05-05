package com.khaithu.a3ctech_android_final_project.sharedprefences

import android.app.Application
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
        if (DataLocalManager.getLanguage().toString().isBlank()) {
            DataLocalManager.setLanguage(LanguageEnum.ENGLISH)
        }
    }
}
