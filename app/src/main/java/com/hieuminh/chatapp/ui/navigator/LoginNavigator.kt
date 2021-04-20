package com.hieuminh.chatapp.ui.navigator

import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface LoginNavigator {

    fun startMainActivity()
    fun startGoogleSignInClient(mGoogleSignInClient : GoogleSignInClient)
}
