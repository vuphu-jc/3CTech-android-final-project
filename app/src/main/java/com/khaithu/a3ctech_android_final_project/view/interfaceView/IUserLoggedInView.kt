package com.khaithu.a3ctech_android_final_project.view.interfaceView

import com.khaithu.a3ctech_android_final_project.model.WatchlistItem

interface IUserLoggedInView {
    fun updateTitle(title : String)

    fun updateWatchlist(watchlistItem: WatchlistItem?)
    fun onRemoveItemWatchListSuccess(id: Int)
}
