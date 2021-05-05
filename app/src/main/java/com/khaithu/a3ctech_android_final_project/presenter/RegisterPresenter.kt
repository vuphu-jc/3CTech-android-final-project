package com.khaithu.a3ctech_android_final_project.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.User
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.IRegisterPresenter
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IRegisterActivity

class RegisterPresenter(var mIRegisterActivity: IRegisterActivity) : IRegisterPresenter {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun register(user: User, password: String, confirmPassword: String) {
        if (password == confirmPassword) {
            firebaseAuth = FirebaseAuth.getInstance()

            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val UID: String? = firebaseAuth.currentUser?.uid
                        saveUserToDatabase(user, UID)
                        mIRegisterActivity.onRegisterSuccess()
                    } else {
                        mIRegisterActivity.onRegisterFail()
                    }
                }
        } else {
            mIRegisterActivity.onRegisterFail()
        }
    }

    private fun saveUserToDatabase(user: User, UID: String?) {
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference(Constant.nodeUser)

        if (UID != null) {
            reference.child(UID).setValue(user)
        }
    }
}
