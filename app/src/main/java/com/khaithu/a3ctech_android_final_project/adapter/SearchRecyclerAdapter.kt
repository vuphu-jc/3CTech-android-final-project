package com.khaithu.a3ctech_android_final_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.adapter.interfaceadapter.IHandleEvent
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.helper.GlideHelper
import com.khaithu.a3ctech_android_final_project.model.ResultMovie
import kotlinx.android.synthetic.main.item_search.view.*

class SearchRecyclerAdapter :
    RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    private lateinit var mHandleEvent: IHandleEvent
    private var lists: List<ResultMovie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_search, parent, false)

        return ViewHolder(view, mHandleEvent)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(lists[position])
    }

    fun updateData(movies: List<ResultMovie>) {
        lists = movies
        notifyDataSetChanged()
    }

    fun initEvent(handlerEvent: IHandleEvent) {
        this.mHandleEvent = handlerEvent
    }

    class ViewHolder(itemView: View, var mHandleEvent: IHandleEvent) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movie: ResultMovie) {
            itemView.titleSearchItem.text = movie.title
            itemView.releaseDateSearchItem.text = movie.releaseDate
            itemView.overviewSearchItem.text = movie.overview
            GlideHelper.loadImage(
                itemView,
                Constant.posterBaseUrl + movie.posterPath,
                itemView.imageSearchItem
            )

            itemView.setOnClickListener(View.OnClickListener {
                mHandleEvent.onClickEvent(movie.id)
            })
        }
    }
}
