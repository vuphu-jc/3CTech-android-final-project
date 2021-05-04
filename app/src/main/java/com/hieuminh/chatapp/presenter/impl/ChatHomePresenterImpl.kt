package com.hieuminh.chatapp.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hieuminh.chatapp.enum.RTDBAttributeName
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.presenter.contract.ChatHomeContract

class ChatHomePresenterImpl(private var view: ChatHomeContract.View?) : ChatHomeContract.Presenter {

    private val mDataBaseReference = FirebaseDatabase.getInstance().reference
    private val mFirebaseAuth = FirebaseAuth.getInstance()

    override fun getValue() {

        mFirebaseAuth.uid?.let { it ->
            mDataBaseReference.child(it).child(RTDBAttributeName.CHAT_ID.index)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {
                            getMessageValues(ds.value.toString())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        view?.onGetValueFail(error.message)
                    }
                })
        }
    }

    override fun onDestroy() {
        view = null
    }

    private fun getMessageValues(chatId: String) {
        mDataBaseReference.child(RTDBAttributeName.CHAT.index).child(chatId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val message: MessageModel? = snapshot.getValue(MessageModel::class.java)
                    message?.let { view?.onGetValueSuccess(it) }
                }

                override fun onCancelled(error: DatabaseError) {
                    view?.onGetValueFail(error.message)
                }
            })
    }
}
