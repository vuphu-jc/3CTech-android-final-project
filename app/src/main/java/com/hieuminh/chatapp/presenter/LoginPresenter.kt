package com.hieuminh.chatapp.presenter

interface LoginPresenter {

    fun login(email: String, password: String)

    fun loginWithGoogle()

    fun loginWithFacebook()

    interface View {
        fun onLoginSuccess(userId: String)
        fun onLoginFail(message: String?)
        fun onStartProcessBar(message: String)
        fun onStopProcessBar()
        fun requestLogin(username: String, password: String)
        fun requestLoginWithGoogle()
        fun requestLoginWithFacebook()
    }
}
