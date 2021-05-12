package com.yoshitoke.weatheringwithyou.mvp.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.Contract
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.*
import com.yoshitoke.weatheringwithyou.mvp.presenter.MainPresenter
import com.yoshitoke.weatheringwithyou.utils.kelvinToCelsius
import com.yoshitoke.weatheringwithyou.utils.toCelsiusString
import com.yoshitoke.weatheringwithyou.utils.unixTimestampToString
import kotlinx.android.synthetic.main.current_conditions_layout.*
import kotlinx.android.synthetic.main.forecast_timeline_layout.*
import kotlinx.android.synthetic.main.location_picking_layout.*

class MainActivity : AppCompatActivity(), Contract.View, AdapterView.OnItemSelectedListener {
    var mPresenter: Contract.Presenter? = null
    private lateinit var mAdapter: TimeLineAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter(this, applicationContext)

        mPresenter?.loadCityList()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        mPresenter?.onLocationSwitched(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }

    override fun showSpinnerList(data: List<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        location_spinner.setAdapter(adapter)
        location_spinner.onItemSelectedListener = this
    }

    override fun showWeatherInfo(data: WeatherInfo) {
        val tempText = data.current.temperature.kelvinToCelsius().toCelsiusString()
        tv_temperature.setText(tempText)

        val datetimeFormat = "dd MMM, yyyy - hh:mm a"
        tv_dateTime.setText(data.current.dateTime.unixTimestampToString(datetimeFormat))
        tv_condition.setText(data.current.weathers[0].description)

        showHourlyForecast(data.hours)
    }

    private fun showHourlyForecast(data: List<Hourly>) {
        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        mAdapter = TimeLineAdapter(data)
        recyclerView.adapter = mAdapter
    }

    override fun onDestroy() {
        mPresenter?.destroy()
        super.onDestroy()
    }
}

