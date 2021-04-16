package com.khaithu.a3ctech_android_final_project.model

import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum

data class MovieRequest(
    var page: Int,
    var apiKey: String,
    var language: LanguageEnum,
    var category: String
)
