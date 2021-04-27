package com.hieuminh.chatapp.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hieuminh.chatapp.presenter.contract.LoginContract

class LoginPresenterImpl(private var view: LoginContract.View?) : LoginContract.Presenter {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String) {
        view?.onStartProcessBar()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                view?.onStopProcessBar()
                if (task.isSuccessful) {
                    view?.onLoginSuccess()
                } else {
                    view?.onLoginFail(task.exception?.message)
                }
            }
    }

    override fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                view?.onUpdateUI(if (task.isSuccessful) mAuth.currentUser else null)
            }
    }

    override fun loginWithFacebook() {
        view?.onLoginWithFacebookFailure()
    }

    override fun onDestroy() {
        view = null
    }
}
