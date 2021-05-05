package com.khaithu.a3ctech_android_final_project.presenter

import com.google.firebase.auth.FirebaseAuth
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.ILoginPresenter
import com.khaithu.a3ctech_android_final_project.view.interfaceView.ILoginDialog

class LoginPresenter(var mILoginDialog: ILoginDialog) : ILoginPresenter {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun login(email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()

        mILoginDialog.loginProgress()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    mILoginDialog.loginSuccess()
                } else {
                    mILoginDialog.loginFail()
                }
            }
    }
}
