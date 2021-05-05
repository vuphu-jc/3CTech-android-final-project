package com.hieuminh.chatapp.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.CHAT
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.CHAT_ID
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.EMAIL
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.ID
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.UID
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.USER_PROFILE
import com.hieuminh.chatapp.enum.ConnectType
import com.hieuminh.chatapp.model.ChatModel
import com.hieuminh.chatapp.model.MessageModel
import com.hieuminh.chatapp.model.UserModel
import com.hieuminh.chatapp.model.UserProfileModel
import com.hieuminh.chatapp.presenter.contract.AddChatContract

class AddChatPresenterImpl(private var view: AddChatContract.View?) : AddChatContract.Presenter {

    private val mFirebaseDatabase = FirebaseDatabase.getInstance().reference
    private val mAuth = FirebaseAuth.getInstance()

    override fun search(email: String) {
        view?.onStartProcessBar()
        mFirebaseDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if (it.child(USER_PROFILE)
                            .child(EMAIL).value == email
                    ) {
                        view?.onStopProcessBar()
                        val userProfile = it.child(USER_PROFILE)
                            .getValue(UserProfileModel::class.java)
                        view?.onSearchSuccess(userProfile)
                        return
                    }
                }
                view?.onStopProcessBar()
                view?.onSearchNotFound()
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onStopProcessBar()
                view?.onSearchFailure(error.message)
            }
        })
    }

    override fun addChat(userProfile: UserProfileModel?) {
        view?.onStartProcessBar()
        if (mAuth.currentUser?.uid == userProfile?.id) {
            view?.onStopProcessBar()
            view?.onAddChatFailureDueToMessageYourself()
        } else {
            mFirebaseDatabase.child(mAuth.currentUser?.uid.toString()).child(CHAT)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            if (it.child(UID).value.toString() == userProfile?.id) {
                                view?.onStopProcessBar()
                                view?.onAddChatFailureDueToUserExistedAndTransferToChatDetail(
                                    it.child(
                                        CHAT_ID
                                    ).value.toString()
                                )
                                return
                            }
                        }
                        addNewChat(userProfile)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        view?.onStopProcessBar()
                        view?.onAddChatFailure(error.message)
                    }
                })
        }
    }

    private fun addNewChat(userProfile: UserProfileModel?) {
        val message = MessageModel()
        message.firstUser = UserModel(
            mAuth.currentUser?.uid,
            mAuth.currentUser?.displayName,
            ConnectType.USER_CONNECT.index,
            mAuth.currentUser?.photoUrl.toString()
        )
        message.secondUser = UserModel(
            userProfile?.id,
            userProfile?.name,
            ConnectType.USER_PARTNER.index,
            userProfile?.avatarUrl
        )
        mFirebaseDatabase.child(CHAT).push().setValue(
            message
        ) { error, ref ->
            view?.onStopProcessBar()
            if (error != null) {
                view?.onAddChatFailure(error.message)
            } else {
                val chatId = ref.key.toString()
                addChatIdForUser(
                    message.firstUser?.id.toString(),
                    message.secondUser?.id.toString(),
                    chatId
                )
                val chatIdMap = HashMap<String, Any>()
                chatIdMap[ID] = chatId
                mFirebaseDatabase.child(CHAT).child(chatId).updateChildren(chatIdMap)
                view?.onAddChatSuccess(chatId)
            }
        }
    }

    override fun onDestroy() {
        view = null
    }

    private fun addChatIdForUser(firstUserId: String, secondUserId: String, chatId: String) {
        mFirebaseDatabase.child(firstUserId).child(CHAT).push()
            .setValue(ChatModel(chatId, secondUserId))
        mFirebaseDatabase.child(secondUserId).child(CHAT).push()
            .setValue(ChatModel(chatId, firstUserId))
    }
}
