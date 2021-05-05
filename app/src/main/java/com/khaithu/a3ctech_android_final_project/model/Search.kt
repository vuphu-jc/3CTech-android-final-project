package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Search(
    @SerializedName("page")
    @Expose
    var page : Int,

    @SerializedName("results")
    @Expose
    var results : List<ResultMovie>,

    @SerializedName("total_pages")
    @Expose
    var totalPage : Int,

    @SerializedName("total_results")
    @Expose
    var totalResult : Int
)
