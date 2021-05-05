package com.hieuminh.chatapp.ui.navigator.Impl

import android.view.View
import androidx.navigation.Navigation
import com.hieuminh.chatapp.ui.home.ChatDetailFragmentDirections
import com.hieuminh.chatapp.ui.navigator.ChatDetailNavigator

class ChatDetailNavigatorImpl(private val view: View) : ChatDetailNavigator {

    override fun returnHomeFragment() {
        val action = ChatDetailFragmentDirections.actionChatDetailFragmentToHomeFragment()
        Navigation.findNavController(view).navigate(action)
    }
}
