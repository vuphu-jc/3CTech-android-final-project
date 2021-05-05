package com.hieuminh.chatapp.model

class UserProfileModel() {
    var id: String? = null
    var name: String? = null
    var phoneNumber: String? = null
    var email: String? = null
    var avatarUrl: String? = null

    constructor(
        uid: String?,
        email: String?,
        displayName: String?,
        photoUrl: String?,
        phoneNumber: String?
    ) : this() {
        this.id = uid
        this.email = email
        this.name = displayName
        this.avatarUrl = photoUrl
        this.phoneNumber = phoneNumber
    }
}
