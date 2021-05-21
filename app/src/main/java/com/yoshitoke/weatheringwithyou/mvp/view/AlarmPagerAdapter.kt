package com.yoshitoke.weatheringwithyou.mvp.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yoshitoke.weatheringwithyou.mvp.presenter.AlarmPresenter

class AlarmPagerAdapter(activity: AppCompatActivity, private val itemsCount: Int, private val presenter: AlarmPresenter) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AlarmWelcomeFragment.getInstance(presenter)
            1 -> AlarmSelectWeatherFragment.getInstance(presenter)
            2 -> AlarmSetTimeFragment.getInstance(presenter)
            else -> AlarmResultFragment.getInstance(presenter)
        }
    }
}
