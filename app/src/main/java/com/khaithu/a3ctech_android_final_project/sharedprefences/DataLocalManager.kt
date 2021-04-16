package com.khaithu.a3ctech_android_final_project.sharedprefences

import android.content.Context

class DataLocalManager {
    private lateinit var mySharedPreference: MySharedPreference

    companion object {
        private lateinit var instance: DataLocalManager

        fun init(context: Context) {
            newInstance()
            instance.mySharedPreference = MySharedPreference(context)
        }

        private fun newInstance(): DataLocalManager {
            if (!this::instance.isInitialized) {
                instance = DataLocalManager()
            }
            return instance
        }
    }
}
