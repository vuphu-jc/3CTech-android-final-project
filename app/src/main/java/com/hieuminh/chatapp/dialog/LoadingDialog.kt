package com.hieuminh.chatapp.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.hieuminh.chatapp.R

class LoadingDialog(private val activity: Activity) {

    private lateinit var alertDialog: AlertDialog

    @SuppressLint("InflateParams")
    fun startLoadingDialog(message : String) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))
        builder.setCancelable(false)
        builder.setMessage(message)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissDialog() {
        alertDialog.dismiss()
    }
}
