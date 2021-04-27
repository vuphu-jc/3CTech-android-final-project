package com.hieuminh.chatapp.presenter.contract

import com.google.firebase.auth.FirebaseUser
import com.hieuminh.chatapp.presenter.BasePresenter

interface LoginContract {

    interface Presenter : BasePresenter {
        fun login(email: String, password: String)
        fun firebaseAuthWithGoogle(idToken: String)
        fun loginWithFacebook()
    }

    interface View {
        fun onUpdateUI(currentUser: FirebaseUser?)
        fun onLoginSuccess()
        fun onLoginFail(message: String?)
        fun onLoginWithGoogleFailure()
        fun onLoginWithFacebookFailure()
        fun onStartProcessBar()
        fun onStopProcessBar()
    }
}
