package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductionCountry(
    @SerializedName("iso_3166_1")
    @Expose
    var iso : String,

    @SerializedName("name")
    @Expose
    var name: String
)
