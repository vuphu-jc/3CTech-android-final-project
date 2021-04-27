package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.khaithu.a3ctech_android_final_project.model.parent.Movie

class ResultMovie(
    adult: Boolean,
    backdropPath: String,
    id: Int,
    originalLanguage: String,
    originalTitle: String,
    overview: String,
    popularity: Double,
    posterPath: String,
    releaseDate: String,
    title: String,
    video: Boolean,
    voteAverage: Double,
    voteCount: Int,

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>

) : Movie(
    adult,
    backdropPath,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)
