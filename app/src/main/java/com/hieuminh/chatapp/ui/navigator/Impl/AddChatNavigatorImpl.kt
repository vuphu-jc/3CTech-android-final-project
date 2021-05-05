package com.hieuminh.chatapp.ui.navigator.Impl

import android.view.View
import androidx.navigation.Navigation
import com.hieuminh.chatapp.ui.home.AddChatFragmentDirections
import com.hieuminh.chatapp.ui.navigator.AddChatNavigator

class AddChatNavigatorImpl(private val view : View) : AddChatNavigator {

    override fun returnHomeFragment() {
        val action = AddChatFragmentDirections.actionAddChatFragmentToHomeFragment()
        Navigation.findNavController(view).navigate(action)
    }

    override fun startChatDetailFragment(chatId : String) {
        val action = AddChatFragmentDirections.actionAddChatFragmentToChatDetailFragment(chatId)
        Navigation.findNavController(view).navigate(action)
    }
}
