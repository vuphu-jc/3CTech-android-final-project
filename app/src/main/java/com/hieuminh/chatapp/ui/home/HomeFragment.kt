package com.hieuminh.chatapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.enum.TabHomeType
import com.hieuminh.chatapp.ui.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var mHomePagerAdapter: HomePagerAdapter
    private lateinit var mContext: FragmentActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FragmentActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initViewPager() {
        mHomePagerAdapter = HomePagerAdapter(mContext.supportFragmentManager, requireContext())
        viewPager.adapter = mHomePagerAdapter
    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(TabHomeType.HOME.index)?.setIcon(R.drawable.ic_baseline_chat_bubble_24)
        tabLayout.getTabAt(TabHomeType.SETTING.index)?.setIcon(R.drawable.ic_baseline_settings_24)
    }

    private fun init() {
        initViewPager()
        initTabLayout()
    }
}
