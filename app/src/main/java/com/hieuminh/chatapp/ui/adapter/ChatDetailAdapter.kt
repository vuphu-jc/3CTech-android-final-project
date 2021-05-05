package com.hieuminh.chatapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.enum.ChatType
import com.hieuminh.chatapp.model.UserModel
import com.hieuminh.chatapp.model.WordModel
import com.hieuminh.chatapp.utils.toStr
import kotlinx.android.synthetic.main.fragment_chat_receive.view.*
import kotlinx.android.synthetic.main.fragment_chat_send.view.*

class ChatDetailAdapter() : BaseAdapter<WordModel, RecyclerView.ViewHolder>() {

    private var ownerUser: UserModel? = null
    private var partnerUser: UserModel? = null

    fun setData(
        ownerUser: UserModel?,
        partnerUser: UserModel?,
        word: MutableList<WordModel> = ArrayList()
    ) {
        this.ownerUser = ownerUser
        this.partnerUser = partnerUser
        this.mDataList = word
        this.notifyDataSetChanged()
    }

    fun updateData(messages : MutableList<WordModel>) {
        this.mDataList = messages
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

       val view = when (viewType) {
            ChatType.SEND.index -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_chat_send, parent, false)
            }
            ChatType.RECEIVE.index -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_chat_receive, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_error_chat_item, parent, false)
            }
        }
        return ChatDetailHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChatDetailHolder) holder.bind(mDataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when (mDataList[position].type) {
            ownerUser?.type -> {
                ChatType.SEND.index
            }
            partnerUser?.type -> {
                ChatType.RECEIVE.index
            }
            else -> {
                ChatType.ERROR.index
            }
        }
    }

    inner class ChatDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(word: WordModel) {
            when (word.type) {
                ownerUser?.type -> {
                    itemView.tvTextSend.text = word.content
                    itemView.tvTimeSend.text = word.time?.toStr()
                    Glide.with(itemView).load(ownerUser?.photoUrl).into(itemView.civAvatarSend)
                }
                partnerUser?.type -> {
                    itemView.tvTextReceive.text = word.content
                    itemView.tvTimeReceive.text = word.time?.toStr()
                    Glide.with(itemView).load(partnerUser?.photoUrl).into(itemView.civAvatarReceive)
                }
            }
        }
    }
}
