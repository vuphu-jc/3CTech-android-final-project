package com.yoshitoke.weatheringwithyou.mvp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.vipulasri.timelineview.TimelineView
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.Hourly
import com.yoshitoke.weatheringwithyou.utils.kelvinToCelsius
import com.yoshitoke.weatheringwithyou.utils.network.ICON_URL_POSTFIX
import com.yoshitoke.weatheringwithyou.utils.network.ICON_URL_PREFIX
import com.yoshitoke.weatheringwithyou.utils.toCelsiusString
import com.yoshitoke.weatheringwithyou.utils.unixTimestampToString
import kotlinx.android.synthetic.main.timeline_item.view.*

class HourlyTimeLineAdapter(private val mFeedList: List<Hourly>) : RecyclerView.Adapter<HourlyTimeLineAdapter.TimeLineViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        if(!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        return TimeLineViewHolder(mLayoutInflater.inflate(R.layout.timeline_item, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val timeLineModel = mFeedList[position]

        val url = ICON_URL_PREFIX + timeLineModel.weathers[0].iconName + ICON_URL_POSTFIX
        Glide.with(holder.itemView.context)
                .load(url)
                .into(holder.image);

        val timeFormat = "hh:mm"
        holder.date.text = timeLineModel.dateTime.unixTimestampToString(timeFormat)
        holder.message.text = timeLineModel.weathers[0].description
        holder.temp.text = timeLineModel.temperature.kelvinToCelsius().toCelsiusString()

        if (position == 0) {
            holder.image.requestLayout()
            holder.image.layoutParams.height = 1000
            holder.image.layoutParams.width = 1000
            holder.date.visibility = View.GONE
            holder.message.visibility = View.GONE
            holder.temp.visibility = View.GONE
        }

    }

    override fun getItemCount() = mFeedList.size

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.text_timeline_date
        val message = itemView.text_timeline_title
        val temp = itemView.text_timeline_temp
        val timeline = itemView.hourly_forcast_timeline
        val image = itemView.circleView

        init {
            timeline.initLine(viewType)
        }
    }

}
