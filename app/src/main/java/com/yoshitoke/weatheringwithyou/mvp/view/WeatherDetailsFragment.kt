package com.yoshitoke.weatheringwithyou.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.WeatherInfo
import com.yoshitoke.weatheringwithyou.utils.*
import com.yoshitoke.weatheringwithyou.utils.DateConstant.Companion.HOUR_FORMAT
import kotlinx.android.synthetic.main.fragment_weather_details.*

class WeatherDetailsFragment : Fragment(){

    companion object {
        const val ARG_DATA = "Weather Data"
        private lateinit var gson: Gson
        private lateinit var weatherInfo: WeatherInfo

        fun getInstance(data: WeatherInfo): Fragment {
            val weatherDetailsFragment = WeatherDetailsFragment()
            gson = Gson()
            val bundle = Bundle()
            bundle.putString(ARG_DATA, gson.toJson(data))

            weatherDetailsFragment.arguments = bundle
            return weatherDetailsFragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_weather_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val json = requireArguments().getString(ARG_DATA)
        weatherInfo = gson.fromJson(json , WeatherInfo::class.java)
        showWeatherDetails()
    }

    private fun showWeatherDetails() {
        val current = weatherInfo.current

        tv_sunrise.text = current.sunrise.unixTimestampToString(HOUR_FORMAT)
        tv_sunset.text = current.sunset.unixTimestampToString(HOUR_FORMAT)
        tv_humidity.text = current.humidity.toPercentageString()
        tv_feels_like.text = current.temp_feels_like.kelvinToCelsius().toCelsiusString()
        tv_wind.text = current.windSpeed.toVelocityString()
        tv_uvi.text = current.uvIndex.toString()
    }
}
