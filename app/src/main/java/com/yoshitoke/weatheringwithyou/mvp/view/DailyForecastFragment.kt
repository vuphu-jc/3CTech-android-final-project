package com.yoshitoke.weatheringwithyou.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.Daily
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo
import com.yoshitoke.weatheringwithyou.mvp.presenter.MainPresenter
import kotlinx.android.synthetic.main.fragment_daily_forecast.*
import kotlinx.android.synthetic.main.fragment_weather_details.*

class DailyForecastFragment : Fragment() {

    companion object {
        const val ARG_DATA = "Weather Data"
        private lateinit var gson: Gson
        private lateinit var weatherInfo: WeatherInfo
        private lateinit var mLayoutManager: LinearLayoutManager
        private lateinit var mAdapter: DailyTimeLineAdapter

        fun getInstance(data: WeatherInfo): Fragment {
            val dailyForecastFragment = DailyForecastFragment()
            gson = Gson()
            val bundle = Bundle()
            bundle.putString(ARG_DATA, gson.toJson(data))

            dailyForecastFragment.arguments = bundle
            return dailyForecastFragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_daily_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val json = requireArguments().getString(ARG_DATA)
        weatherInfo = gson.fromJson(json , WeatherInfo::class.java)
        showDailyTimeLine(weatherInfo.days)
    }

    private fun showDailyTimeLine(data: List<Daily>) {
        mLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        daily_forecast_recyclerView.layoutManager = mLayoutManager
        mAdapter = DailyTimeLineAdapter(data)
        daily_forecast_recyclerView.adapter = mAdapter
    }

}
