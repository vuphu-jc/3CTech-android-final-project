package com.hieuminh.chatapp.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.enum.TabHomeType
import com.hieuminh.chatapp.ui.home.ChatHomeFragment
import com.hieuminh.chatapp.ui.home.SettingHomeFragment

class HomePagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments: List<Fragment> = arrayListOf(ChatHomeFragment(), SettingHomeFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            TabHomeType.HOME.index -> context.getString(R.string.chat_view_tab)
            TabHomeType.SETTING.index -> context.getString(R.string.setting_view_tab)
            else -> context.resources.getString(R.string.empty)
        }
    }
}
