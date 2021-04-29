package com.hieuminh.chatapp.ui.navigator.Impl

import android.view.View
import androidx.navigation.Navigation
import com.hieuminh.chatapp.ui.home.HomeFragmentDirections
import com.hieuminh.chatapp.ui.navigator.HomeNavigator

class HomeNavigatorImpl(private val view : View) : HomeNavigator {

    override fun startChatDetailFragment(chatId : String?) {
        val action = HomeFragmentDirections.actionHomeFragmentToChatDetailFragment(chatId.toString())
        Navigation.findNavController(view).navigate(action)
    }
}
