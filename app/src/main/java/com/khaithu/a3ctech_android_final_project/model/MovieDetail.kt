package com.khaithu.a3ctech_android_final_project.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.khaithu.a3ctech_android_final_project.model.parent.Movie

class MovieDetail(
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

    @SerializedName("belongs_to_collection")
    @Expose
    var belongsToCollection: Any,

    @SerializedName("budget")
    @Expose
    var budget: Int,

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>,

    @SerializedName("homepage")
    @Expose
    var homepage: String,

    @SerializedName("imdb_id")
    @Expose
    var imdbId: String,

    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>,

    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry>,

    @SerializedName("revenue")
    @Expose
    var revenue: Int,

    @SerializedName("runtime")
    @Expose
    var runtime: Int,

    @SerializedName("spoken_language")
    @Expose
    var spokenLanguages: List<SpokenLanguage>,

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("tagline")
    @Expose
    var tagline: String
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
