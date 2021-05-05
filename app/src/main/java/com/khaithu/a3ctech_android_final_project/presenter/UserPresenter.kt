package com.khaithu.a3ctech_android_final_project.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.WatchlistItem
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.IUserPresenter
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IUserLoggedInView

class UserPresenter(var mIUserLoggedInView: IUserLoggedInView) : IUserPresenter {

    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun getTitle() {
        firebaseAuth = FirebaseAuth.getInstance()
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.reference

        firebaseAuth.currentUser?.uid?.let {
            reference.child(Constant.nodeUser).child(it).child(Constant.nodeFullName).get()
                .addOnSuccessListener { task ->
                     mIUserLoggedInView.updateTitle(task.value.toString())
                }
        }
    }

    override fun createWatchlist() {
        firebaseAuth = FirebaseAuth.getInstance()
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.reference

        firebaseAuth.currentUser?.uid?.let {
            reference.child(Constant.nodeUser).child(it).child(Constant.nodeWatchlist)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { task ->
                            mIUserLoggedInView.updateWatchlist(task.getValue(WatchlistItem::class.java))
                        }
                    }
                    override fun onCancelled(error: DatabaseError) = Unit
                })
        }
    }

    override fun removeWatchlist(id : Int) {
        firebaseAuth = FirebaseAuth.getInstance()
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.reference

        firebaseAuth.currentUser?.uid?.let {
            reference.child(Constant.nodeUser).child(it).child(Constant.nodeWatchlist).child(id.toString()).removeValue().addOnSuccessListener {
                mIUserLoggedInView.onRemoveItemWatchListSuccess(id)
            }
        }
    }
}
