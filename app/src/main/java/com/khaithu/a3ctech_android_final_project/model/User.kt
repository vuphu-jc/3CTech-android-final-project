package com.khaithu.a3ctech_android_final_project.model

import android.text.TextUtils
import android.util.Patterns

class User(
    var fullName : String,
    var email : String
) {
    fun isValidEmail() : Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
