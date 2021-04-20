package com.hieuminh.chatapp.presenter.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.presenter.LoginPresenter
import com.hieuminh.chatapp.utils.SessionUtil

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginPresenterImpl(private val context: Context, private val view: LoginPresenter.View) : LoginPresenter {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String) {
        view.onStartProcessBar(context.resources.getString(R.string.login_loading))
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                view.onStopProcessBar()
                if (task.isSuccessful) {
                    val userId: String = mAuth.currentUser.uid
                    SessionUtil(context).userId = userId
                    view.onLoginSuccess(userId)
                } else {
                    val message: String? = task.exception?.message
                    view.onLoginFail(message)
                }
            }
    }

    override fun loginWithGoogle() {
        view.onLoginFail(context.getString(R.string.login_with_google_is_not_available))
    }

    override fun loginWithFacebook() {
        view.onLoginFail(context.getString(R.string.login_with_facebook_is_not_available))
    }
}
