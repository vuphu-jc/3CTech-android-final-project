package com.hieuminh.chatapp.model

class MessageModel() {
    var firstUser : UserModel? = null
    var secondUser: UserModel? = null
    var messages : MutableList<WordModel>? = null
    var id : String? = null
}
