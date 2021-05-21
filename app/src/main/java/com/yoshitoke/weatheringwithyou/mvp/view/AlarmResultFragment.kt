package com.yoshitoke.weatheringwithyou.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.alarm.AlarmData
import com.yoshitoke.weatheringwithyou.mvp.AlarmContract
import com.yoshitoke.weatheringwithyou.mvp.presenter.AlarmPresenter
import kotlinx.android.synthetic.main.fragment_alarm_result.*

class AlarmResultFragment(private val presenter: AlarmPresenter) : Fragment(), AlarmContract.View.FinalResultView{

    companion object {
        fun getInstance(presenter: AlarmPresenter): Fragment {
            val alarmResultFragment = AlarmResultFragment(presenter)

            return alarmResultFragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_alarm_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.setResultFragmentView(this)
        edit_btn.setOnClickListener{
            presenter.onEditedPressed()
        }
    }

    override fun onAlarmSet(alarmData: AlarmData) {
        val hourText = "Hour: " + alarmData.hour.toString()
        val minuteText = "Minute: " + alarmData.minute.toString()
        val daysText = "Every: " + alarmData.days.toString()

        tv_hours.text = hourText
        tv_minute.text = minuteText
        tv_days.text = daysText
    }

}
