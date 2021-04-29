package com.hieuminh.chatapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.presenter.contract.ChatHomeContract
import com.hieuminh.chatapp.presenter.impl.ChatHomePresenterImpl
import com.hieuminh.chatapp.ui.adapter.ChatHomeAdapter
import com.hieuminh.chatapp.ui.adapter.OnItemChatHomeClickListener
import com.hieuminh.chatapp.ui.navigator.HomeNavigator
import com.hieuminh.chatapp.ui.navigator.Impl.HomeNavigatorImpl
import kotlinx.android.synthetic.main.fragment_chat_home.*

class ChatHomeFragment : Fragment(), ChatHomeContract.View, OnItemChatHomeClickListener {

    private lateinit var mChatHomePresenter: ChatHomeContract.Presenter
    private lateinit var mChatHomeAdapter: ChatHomeAdapter
    private lateinit var mHomeNavigator: HomeNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override fun onDestroy() {
        mChatHomeAdapter.setOnItemClickListener(null)
        mChatHomePresenter.onDestroy()
        super.onDestroy()
    }

    override fun onItemClick(chatId : String?) {
        mHomeNavigator.startChatDetailFragment(chatId)
    }

    override fun onGetValueFail(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onGetValueSuccess(messages: MessageModel) {
        mChatHomeAdapter.updateData(listOf(messages))
    }

    private fun initAdapter() {
        mChatHomeAdapter = ChatHomeAdapter()
        mChatHomeAdapter.setOnItemClickListener(this)
        mChatHomePresenter.getValue()
    }

    private fun initRecyclerView() {
        rvMessages.layoutManager = LinearLayoutManager(context)
        rvMessages.setHasFixedSize(true)
        rvMessages.adapter = mChatHomeAdapter
    }

    private fun init(view : View) {
        mChatHomePresenter = ChatHomePresenterImpl(this)
        mHomeNavigator = HomeNavigatorImpl(view)
        initAdapter()
        initRecyclerView()
    }
}
