package com.hieuminh.chatapp.model

class ChatModel() {
    var chatId: String? = null
    var uid: String? = null

    constructor(chatId: String?, uid: String?) : this() {
        this.chatId = chatId
        this.uid = uid
    }
}
