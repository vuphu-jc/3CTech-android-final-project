package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("id")
    @Expose
    var id : Int,

    @SerializedName("name")
    @Expose
    var name : String
)
