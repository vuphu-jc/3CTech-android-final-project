package com.hieuminh.chatapp.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.hieuminh.chatapp.constant.Constant.RTDBFirebaseAttrsName.USER_PROFILE
import com.hieuminh.chatapp.model.UserProfileModel
import com.hieuminh.chatapp.presenter.contract.LoginContract

class LoginPresenterImpl(private var view: LoginContract.View?) : LoginContract.Presenter {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDataBaseReference = FirebaseDatabase.getInstance().reference

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
                if (task.isSuccessful) {
                    val userProfile = UserProfileModel(
                        mAuth.uid,
                        mAuth.currentUser?.email,
                        mAuth.currentUser?.displayName,
                        mAuth.currentUser?.photoUrl?.toString(),
                        mAuth.currentUser?.phoneNumber
                    )
                    val userProfileMap = HashMap<String, Any>()
                    userProfileMap[USER_PROFILE] = userProfile
                    mAuth.uid?.let { mDataBaseReference.child(it).updateChildren(userProfileMap) }
                    view?.onUpdateUI(mAuth.currentUser)
                } else {
                    view?.onUpdateUI(null)
                }
            }
    }

    override fun loginWithFacebook() {
        view?.onLoginWithFacebookFailure()
    }

    override fun onDestroy() {
        view = null
    }
}
