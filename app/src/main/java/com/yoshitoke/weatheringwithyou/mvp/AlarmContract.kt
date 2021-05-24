package com.yoshitoke.weatheringwithyou.mvp

import com.yoshitoke.weatheringwithyou.alarm.AlarmData
import com.yoshitoke.weatheringwithyou.mvp.presenter.BasePresenter

interface AlarmContract {
    interface View {
        interface FinalResultView {
            fun onAlarmSet(alarmData: AlarmData)
        }

        fun onNextViewTransition()

        fun onSpecificViewTransition(position: Int)

    }

    interface Presenter: BasePresenter {

        fun updateTime(hourOfDay: Int, minute: Int)

        fun updateDaysOfWeek(days: List<String>)

        fun updateWeatherType(checkedList: List<String>)

        fun confirmSetting()

        fun nextPage()

        fun checkPreviousSetting()

        fun switchToFirstPage()

        fun setResultView(view: View.FinalResultView)

    }
}
