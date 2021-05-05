package com.hieuminh.chatapp.presenter.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.CHAT
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.MESSAGES
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.model.WordModel
import com.hieuminh.chatapp.presenter.contract.ChatDetailContract

class ChatDetailPresenterImpl(private var view: ChatDetailContract.View?) :
    ChatDetailContract.Presenter {

    private val mDataBaseReference = FirebaseDatabase.getInstance().reference
    private var mWords : MutableList<WordModel>? = null

    override fun getMessage(chatId: String) {
        mDataBaseReference.child(CHAT).child(chatId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val message = snapshot.getValue(MessageModel::class.java)
                message?.let {
                    mWords = message.messages
                    view?.onGetMessageSuccess(message)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onGetMessageFailure(error.message)
            }
        })
    }

    override fun sendMessage(word: WordModel, chatId: String) {
        val messagesMap = HashMap<String, Any>()
        val words = mWords
        if(words == null) {
            messagesMap[MESSAGES] = listOf(word)
        } else {
            words.add(0, word)
            messagesMap[MESSAGES] = words
        }
        mDataBaseReference
            .child(CHAT)
            .child(chatId)
            .updateChildren(messagesMap) { error, _ ->
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
