package com.hieuminh.chatapp.presenter.contract

import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.presenter.BasePresenter

interface ChatHomeContract {

    interface Presenter : BasePresenter {
        fun getValue()
    }

    interface View {
        fun onGetValueFail(errorMessage : String)
        fun onGetValueSuccess(messages : MessageModel)
    }
}
