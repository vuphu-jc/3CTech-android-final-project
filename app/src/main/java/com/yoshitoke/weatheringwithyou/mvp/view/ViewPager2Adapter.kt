package com.yoshitoke.weatheringwithyou.mvp.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo

class ViewPager2Adapter(activity: AppCompatActivity, private val itemsCount: Int, private val data: WeatherInfo) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DailyForecastFragment.getInstance(data)
            else -> WeatherDetailsFragment.getInstance(data)
        }
    }
}
