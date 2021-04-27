package com.hieuminh.chatapp.ui.navigator.Impl

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.hieuminh.chatapp.constant.Constant
import com.hieuminh.chatapp.ui.MainActivity
import com.hieuminh.chatapp.ui.navigator.LoginNavigator

class LoginNavigatorImpl(private val activity: Activity) :
    LoginNavigator {

    override fun startMainActivity() {
        val intent = Intent(activity.application, MainActivity::class.java)
        activity.startActivity(intent)
    }

    override fun startGoogleSignInClient(mGoogleSignInClient: GoogleSignInClient) {
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, Constant.GoogleKey.RC_SIGN_IN)
    }
}
