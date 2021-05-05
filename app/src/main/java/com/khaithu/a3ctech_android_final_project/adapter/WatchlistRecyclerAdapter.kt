package com.khaithu.a3ctech_android_final_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.helper.GlideHelper
import com.khaithu.a3ctech_android_final_project.model.WatchlistItem
import kotlinx.android.synthetic.main.item_watchlist.view.*

class WatchlistRecyclerAdapter : RecyclerView.Adapter<WatchlistRecyclerAdapter.ViewHolder>() {

    private lateinit var mIWatchListEvent: IWatchListEvent
    private var mLists: MutableList<WatchlistItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_watchlist, parent, false)

        return ViewHolder(view, mIWatchListEvent)
    }

    override fun getItemCount(): Int {
        return mLists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mLists[position])
    }

    fun updateData(lists: MutableList<WatchlistItem>) {
        this.mLists = lists
        notifyDataSetChanged()
    }

    fun initEvent(handlerEvent: IWatchListEvent) {
        this.mIWatchListEvent = handlerEvent
    }

    fun removeItem(id: Int) {
        val index = mLists.indexOfFirst { it.id == id }
        if (index != -1) {
            mLists.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    class ViewHolder(itemView: View, var mIWatchListEvent: IWatchListEvent) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(movie: WatchlistItem) {
            itemView.titleMovieWatchlist.text = movie.title
            itemView.releaseDateWatchlist.text = movie.releaseDate

            GlideHelper.loadImage(
                itemView,
                Constant.posterBaseUrl + movie.posterImage,
                itemView.imageMovieWatchlist
            )

            itemView.imageMovieWatchlist.setOnClickListener(View.OnClickListener {
                mIWatchListEvent.onMovieWatchlistClicked(movie.id)
            })

            itemView.textRemove.setOnClickListener(View.OnClickListener {
                mIWatchListEvent.onMovieWatchlistRemoved(movie.id)
            })
        }
    }
}
interface IWatchListEvent {
    fun onMovieWatchlistClicked(id: Int?)

    fun onMovieWatchlistRemoved(id: Int?)
}
