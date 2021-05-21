package com.yoshitoke.weatheringwithyou.mvp.view

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.AlarmContract
import com.yoshitoke.weatheringwithyou.mvp.presenter.AlarmPresenter

import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.bottom_tabs_layout.*

class AlarmActivity : AppCompatActivity(), AlarmContract.View {
    var mPresenter: AlarmContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        mPresenter = AlarmPresenter(this, applicationContext)
        val viewPagerAdapter = AlarmPagerAdapter(this, 4, mPresenter as AlarmPresenter)
        screenPager.adapter = viewPagerAdapter

        mPresenter?.checkPreviousSetting()
    }

    override fun onNextViewTransition() {
        screenPager.currentItem = screenPager.currentItem + 1
    }

    override fun onSpecificViewTransition(position: Int) {
        screenPager.currentItem = position
    }
//    override fun onBackPressed() {
//        setResult(RESULT_CANCELED)
//        super.onBackPressed()
//    }

}
