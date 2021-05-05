package com.khaithu.a3ctech_android_final_project.model

class WatchlistItem() {
    var id: Int? = null
    var title: String? = null
    var releaseDate: String? = null
    var posterImage: String? = null
    var rate: Double? = null

    constructor(
        id: Int?,
        title: String?,
        releaseDate: String?,
        posterImage: String?,
        rate: Double?
    ) : this() {
        this.id = id
        this.title = title
        this.releaseDate = releaseDate
        this.posterImage = posterImage
        this.rate = rate
    }
}
