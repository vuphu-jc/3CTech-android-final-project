package com.yoshitoke.weatheringwithyou.mvp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.vipulasri.timelineview.TimelineView
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.Daily
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.Hourly
import com.yoshitoke.weatheringwithyou.utils.kelvinToCelsius
import com.yoshitoke.weatheringwithyou.utils.network.ICON_URL_POSTFIX
import com.yoshitoke.weatheringwithyou.utils.network.ICON_URL_PREFIX
import com.yoshitoke.weatheringwithyou.utils.toCelsiusString
import com.yoshitoke.weatheringwithyou.utils.unixTimestampToString
import kotlinx.android.synthetic.main.timeline_item.view.*

class DailyTimeLineAdapter(private val mFeedList: List<Daily>) : RecyclerView.Adapter<DailyTimeLineAdapter.TimeLineViewHolder>() {

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

        val timeFormat = "dd MMM"
        holder.date.text = timeLineModel.dateTime.unixTimestampToString(timeFormat)
        holder.message.text = timeLineModel.weathers[0].description

        val tempText = timeLineModel.temperature.max.kelvinToCelsius().toCelsiusString() + " - " + timeLineModel.temperature.min.kelvinToCelsius().toCelsiusString()
        holder.temp.text = tempText
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
