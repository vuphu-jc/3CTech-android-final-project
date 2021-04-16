package com.khaithu.a3ctech_android_final_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.model.SliderData
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_slider.view.*

class SliderAdapter(var context: Context, var sliderItems: List<SliderData>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        var view: View = LayoutInflater.from(parent?.context).inflate(R.layout.item_slider, null)

        return SliderViewHolder(view)
    }

    override fun getCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
       viewHolder.bind(sliderItems[position])
    }

    class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

        fun bind(sliderData: SliderData) {
            Glide.with(itemView).load(sliderData.urlImage).into(itemView.imageItemSlider)
        }
    }
}

