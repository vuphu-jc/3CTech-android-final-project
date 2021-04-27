package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Video(
    @SerializedName("id")
    @Expose
    var id : Int,

    @SerializedName("results")
    @Expose
    var resultVideos : List<ResultVideo>
)
