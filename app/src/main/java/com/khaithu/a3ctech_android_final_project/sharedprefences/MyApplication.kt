package com.khaithu.a3ctech_android_final_project.sharedprefences

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
    }
}
