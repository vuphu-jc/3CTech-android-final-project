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

        fun onSelectedTime(hourOfDay: Int, minute: Int)

        fun onSelectedDaysOfWeek(days: List<String>)

        fun onSelectedWeatherType(checkedList: List<String>)

        fun onConfirmSetting()

        fun onNextButtonPressed()

        fun checkPreviousSetting()

        fun onEditedPressed()

        fun setResultFragmentView(fragment: View.FinalResultView)

    }
}
