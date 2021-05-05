package com.hieuminh.chatapp.model

class UserModel() {
    var id : String? = null
    var name : String? = null
    var type : Int? = null

    constructor(id : String?, name : String?, type : Int?) : this() {
        this.id = id
        this.name = name
        this.type = type
    }
}
