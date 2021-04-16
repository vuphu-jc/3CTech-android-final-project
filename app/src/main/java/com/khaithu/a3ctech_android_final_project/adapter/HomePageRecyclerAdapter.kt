package com.khaithu.a3ctech_android_final_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.adapter.interfaceadapter.IHandleEvent
import com.khaithu.a3ctech_android_final_project.model.ResultMovie
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.helper.GlideHelper
import kotlinx.android.synthetic.main.item_movies.view.*

class HomePageRecyclerAdapter(var mHandleEvent: IHandleEvent) :
    RecyclerView.Adapter<HomePageRecyclerAdapter.ViewHolder>() {

    private var lists: List<ResultMovie> = emptyList()

    fun updateData(movies: List<ResultMovie>) {
        lists = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_movies, parent, false)

        return ViewHolder(view, mHandleEvent)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(lists[position])
    }

    class ViewHolder(itemView: View, var mHandleEvent: IHandleEvent) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movie: ResultMovie) {
            itemView.titleMovie.text = movie.title
            itemView.releaseDate.text = movie.releaseDate
            itemView.rateView.setPercent((movie.voteAverage * 10).toInt())

            GlideHelper.loadImage(
                itemView,
                Constant.posterBaseUrl + movie.posterPath,
                itemView.imageMovie
            )

            itemView.setOnClickListener(View.OnClickListener {
                mHandleEvent.onClickEvent(movie.id)
            })
        }
    }
}

