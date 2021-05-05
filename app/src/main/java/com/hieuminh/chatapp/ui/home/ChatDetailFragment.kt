package com.hieuminh.chatapp.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.dialog.CustomAlertDialog
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.model.UserModel
import com.hieuminh.chatapp.model.WordModel
import com.hieuminh.chatapp.presenter.contract.ChatDetailContract
import com.hieuminh.chatapp.presenter.impl.ChatDetailPresenterImpl
import com.hieuminh.chatapp.ui.adapter.ChatDetailAdapter
import com.hieuminh.chatapp.ui.navigator.ChatDetailNavigator
import com.hieuminh.chatapp.ui.navigator.Impl.ChatDetailNavigatorImpl
import kotlinx.android.synthetic.main.fragment_chat_detail.*
import kotlinx.android.synthetic.main.fragment_chat_detail.view.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class ChatDetailFragment : Fragment(), ChatDetailContract.View {

    private lateinit var mChatDetailNavigator: ChatDetailNavigator
    private lateinit var mChatDetailAdapter: ChatDetailAdapter
    private lateinit var mChatDetailPresenter: ChatDetailContract.Presenter
    private lateinit var chatId: String
    private var isCheckTransformData = false
    private lateinit var mView: View
    private val mAuth = FirebaseAuth.getInstance()
    private var ownerUser: UserModel? = null
    private var partnerUser: UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView =
            inflater.inflate(R.layout.fragment_chat_detail, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        arguments?.let {
            val args = ChatDetailFragmentArgs.fromBundle(it)
            chatId = args.chatId
            mChatDetailPresenter.getMessage(chatId)
        }

        tvReturnChatHome.setOnClickListener(onReturnChatHomeListener())

        ivSend.setOnClickListener(onSendListener())
    }

    private fun onSendListener() = View.OnClickListener {
        val word = WordModel()
        word.type = ownerUser?.type
        word.content = etText.text.toString().trim()
        word.time = Calendar.getInstance().time
        mChatDetailPresenter.sendMessage(word, chatId)
    }

    override fun onDestroy() {
        mChatDetailPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onGetMessageSuccess(message: MessageModel) {
        if (!isCheckTransformData) {
            if (mAuth.uid == message.firstUser?.id) {
                ownerUser = message.firstUser
                partnerUser = message.secondUser
            } else {
                ownerUser = message.secondUser
                partnerUser = message.firstUser
            }
            val a = message.messages
            if(a != null) mChatDetailAdapter.setData(ownerUser, partnerUser, a)
            else mChatDetailAdapter.setData(ownerUser, partnerUser)
            mView.tvName.text = partnerUser?.name
            Glide.with(mView).load(partnerUser?.photoUrl).into(mView.civAvatar)
            isCheckTransformData = true
        } else {
            message.messages?.let { mChatDetailAdapter.updateData(it) }
        }
        mView.rv_chat.scrollToPosition(0)
    }

    override fun onGetMessageFailure(errorMessage: String) {
        CustomAlertDialog(requireContext()).startAlertDialog(errorMessage)
    }

    override fun onSendSuccess() {
        mView.etText.text.clear()
    }

    override fun onSendFailure(errorMessage: String) {
        CustomAlertDialog(requireContext()).startAlertDialog(errorMessage)
    }

    private fun onReturnChatHomeListener() =
        View.OnClickListener { mChatDetailNavigator.returnHomeFragment() }

    private fun initAdapter() {
        mChatDetailAdapter = ChatDetailAdapter()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(null)
        layoutManager.reverseLayout = true
        mView.rv_chat.layoutManager = layoutManager
        mView.rv_chat.setHasFixedSize(true)
        mView.rv_chat.adapter = mChatDetailAdapter
    }

    private fun init(view: View) {
        mChatDetailNavigator = ChatDetailNavigatorImpl(view)
        mChatDetailPresenter = ChatDetailPresenterImpl(this)
        initAdapter()
        initRecyclerView()
    }
}
