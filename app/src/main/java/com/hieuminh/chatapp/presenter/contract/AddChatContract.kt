package com.hieuminh.chatapp.presenter.contract

import com.hieuminh.chatapp.model.UserProfileModel
import com.hieuminh.chatapp.presenter.BasePresenter

interface AddChatContract {

    interface Presenter : BasePresenter {
        fun search(email : String)
        fun addChat(userProfile: UserProfileModel?)
    }

    interface View {
        fun onSearchSuccess(userProfile : UserProfileModel?)
        fun onSearchFailure(message: String)
        fun onSearchNotFound()
        fun onAddChatSuccess(chatId: String)
        fun onAddChatFailure(message : String)
        fun onAddChatFailureDueToMessageYourself()
        fun onStartProcessBar()
        fun onStopProcessBar()
        fun onAddChatFailureDueToUserExistedAndTransferToChatDetail(chatId : String)
    }
}
