package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductionCompany(
    @SerializedName("name")
    @Expose
    var name : String,

    @SerializedName("id")
    @Expose
    var id : Int,

    @SerializedName("logo_path")
    @Expose
    var logoPath : String,

    @SerializedName("origin_country")
    @Expose
    var originCountry : String
)
