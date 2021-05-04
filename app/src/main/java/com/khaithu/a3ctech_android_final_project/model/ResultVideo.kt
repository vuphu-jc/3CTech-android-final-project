package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.khaithu.a3ctech_android_final_project.model.enum.VideoEnum

class ResultVideo(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("iso_639_1")
    @Expose
    var isoLanguage: String,

    @SerializedName("iso_3166_1")
    @Expose
    var isoNation: String,

    @SerializedName("key")
    @Expose
    var key: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("site")
    @Expose
    var site: String,

    @SerializedName("size")
    @Expose
    var size: Int,

    @SerializedName("type")
    @Expose
    var type: String
) {
    fun isTrailer(): Boolean = type == VideoEnum.TRAILER.value
}
