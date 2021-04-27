package com.hieuminh.chatapp.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.hieuminh.chatapp.R

class LoadingDialog(context: Context) {

    private val mAlertDialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        builder.setView(View.inflate(context, R.layout.custom_dialog, null))
        builder.setCancelable(false)
        mAlertDialog = builder.create()
    }

    fun startLoadingDialog(message : String) {
        mAlertDialog.setMessage(message)
        mAlertDialog.show()
    }

    fun dismissDialog() {
        mAlertDialog.dismiss()
    }
}
