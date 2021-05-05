package com.hieuminh.chatapp.model

class UserModel() {
    var id : String? = null
    var name : String? = null
    var type : Int? = null
    var photoUrl : String? = null

    constructor(id : String?, name : String?, type : Int?, photoUrl : String?) : this() {
        this.id = id
        this.name = name
        this.type = type
        this.photoUrl = photoUrl
    }
}
