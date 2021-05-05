package com.hieuminh.chatapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.model.UserModel
import com.hieuminh.chatapp.model.WordModel
import com.hieuminh.chatapp.utils.lastName
import com.hieuminh.chatapp.utils.toStr
import kotlinx.android.synthetic.main.fragment_message_item.view.*

class ChatHomeAdapter() :
    BaseAdapter<MessageModel, RecyclerView.ViewHolder>() {

    private val mAuth = FirebaseAuth.getInstance()

    private var mOnItemClickListener: OnItemChatHomeClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHomeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_message_item, parent, false)
        return ChatHomeHolder(view, mOnItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChatHomeHolder) holder.bind(mDataList[position])
    }

    fun setOnItemClickListener(onItemChatHomeClickListener: OnItemChatHomeClickListener?) {
        mOnItemClickListener = onItemChatHomeClickListener
    }

    inner class ChatHomeHolder(
        private val view: View,
        private val onItemClickListener: OnItemChatHomeClickListener?
    ) : RecyclerView.ViewHolder(view) {

        private val mContext = view.context

        init {
            handleEvents()
        }

        fun bind(messageModel: MessageModel) {
            val ownerUser: UserModel?
            val partnerUser: UserModel?
            if (mAuth.uid == messageModel.firstUser?.id) {
                ownerUser = messageModel.firstUser
                partnerUser = messageModel.secondUser
            } else {
                ownerUser = messageModel.secondUser
                partnerUser = messageModel.firstUser
            }
            view.tvNameItem.text = partnerUser?.name
            val lastWord: WordModel? = messageModel.messages?.first()
            val lastWordText =
                if (lastWord?.type == ownerUser?.type) mContext.getString(R.string.you) + lastWord?.content
                else partnerUser?.name?.lastName() + mContext.getString(R.string.blank) + lastWord?.content

            view.tvLastWordItem.text = lastWordText
            view.tvLastTimeItem.text = lastWord?.time?.toStr()
        }

        private fun handleEvents() {
            view.llContainerMessageItem.setOnClickListener {
                onItemClickListener?.onItemClick(mDataList[layoutPosition].id)
            }
        }
    }
}

interface OnItemChatHomeClickListener {
    fun onItemClick(chatId: String?)
}
