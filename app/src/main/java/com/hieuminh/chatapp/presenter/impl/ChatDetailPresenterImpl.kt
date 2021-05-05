package com.hieuminh.chatapp.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.CHAT
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.CHAT_ID
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.MESSAGES
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.model.WordModel
import com.hieuminh.chatapp.presenter.contract.ChatDetailContract

class ChatDetailPresenterImpl(private var view: ChatDetailContract.View?) :
    ChatDetailContract.Presenter {

    private val mDataBaseReference = FirebaseDatabase.getInstance().reference
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var messageModel: MessageModel? = null

    override fun getMessage(chatId: String) {
        mDataBaseReference.child(CHAT).child(chatId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val message = snapshot.getValue(MessageModel::class.java)
                message?.let {
                    messageModel = message
                    view?.onGetMessageSuccess(message)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onGetMessageFailure(error.message)
            }
        })
    }

    override fun sendMessage(word: WordModel, chatId: String) {
        messageModel?.messages?.add(0, word)
        val messagesMap = HashMap<String, Any>()
        messageModel?.messages?.let { messagesMap.put(MESSAGES, it) }
        mDataBaseReference
            .child(CHAT)
            .child(chatId)
            .updateChildren(messagesMap) { error, ref ->
                if (error != null) {
                    view?.onSendFailure(error.message)
                } else {
                    view?.onSendSuccess()
                }
            }
    }

    override fun onDestroy() {
        view = null
    }
}
