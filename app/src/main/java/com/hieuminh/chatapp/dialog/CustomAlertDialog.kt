package com.hieuminh.chatapp.dialog

import android.app.AlertDialog
import android.content.Context
import com.hieuminh.chatapp.R

class CustomAlertDialog(context: Context) {

    private val mAlertDialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        builder.setNegativeButton(context.getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
        mAlertDialog = builder.create()
        mAlertDialog.setTitle(context.getString(R.string.notice))
    }

    fun startAlertDialog(message: String?) {
        mAlertDialog.setMessage(message)
        mAlertDialog.show()
    }
}
