package com.khaithu.a3ctech_android_final_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.model.ResultMovie
import kotlinx.android.synthetic.main.item_movie.view.*

class HomePageRecyclerAdapter :
    RecyclerView.Adapter<HomePageRecyclerAdapter.ViewHolder>() {

    private var lists: List<ResultMovie> = listOf()

    fun updateData(movies: List<ResultMovie>) {
        lists = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(lists[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val baseImgUrl = "https://image.tmdb.org/t/p/w500"

        fun bind(movie: ResultMovie) {
            itemView.titleMovie.text = movie.title
            itemView.releaseDate.text = movie.releaseDate
            Glide.with(itemView).load(baseImgUrl + movie.posterPath)
                .into(itemView.imageMovie)
        }
    }
}

