package com.hieuminh.chatapp.presenter.contract

import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.model.WordModel
import com.hieuminh.chatapp.presenter.BasePresenter
import com.hieuminh.chatapp.ui.adapter.BaseAdapter

class ChatDetailContract {

    interface Presenter : BasePresenter {
        fun getMessage(chatId: String)
        fun sendMessage(word : WordModel, chatId : String)
    }

    interface View {
        fun onGetMessageSuccess(message: MessageModel)
        fun onGetMessageFailure(errorMessage: String)
        fun onSendSuccess()
        fun onSendFailure(errorMessage: String)
    }
}
