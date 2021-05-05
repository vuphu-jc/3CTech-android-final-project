package com.khaithu.a3ctech_android_final_project.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.extension.gone
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.presenter.LoginPresenter
import com.khaithu.a3ctech_android_final_project.sharedprefences.DataLocalManager
import com.khaithu.a3ctech_android_final_project.view.RegisterActivity
import com.khaithu.a3ctech_android_final_project.view.fragment.UserLoggedInView
import com.khaithu.a3ctech_android_final_project.view.fragment.UserLoggedOutView
import com.khaithu.a3ctech_android_final_project.view.interfaceView.ILoginDialog
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IUserLoggedOutView
import kotlinx.android.synthetic.main.login_dialog.*

class LoginDialog() : DialogFragment(), ILoginDialog {

    private var mPresenter: LoginPresenter = LoginPresenter(this)
    private var mProgressBar: ProgressBar? = null

    private lateinit var mIUserLoggedOutView: IUserLoggedOutView

    constructor(userLoggedOutView: IUserLoggedOutView) : this() {
        this.mIUserLoggedOutView = userLoggedOutView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View? = activity?.layoutInflater?.inflate(R.layout.login_dialog, null)
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle(context?.getString(R.string.login))
        builder?.setView(view)

        val alertDialog: AlertDialog? = builder?.create()

        val textRes: TextView? = view?.findViewById(R.id.textRegister)
        val buttonLogin: Button? = view?.findViewById(R.id.buttonLogin)
        val buttonCancel: Button? = view?.findViewById(R.id.buttonCancel)
        mProgressBar = view?.findViewById(R.id.loginProgress)

        textRes?.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        })
        buttonLogin?.setOnClickListener(View.OnClickListener {
            val textEmail: EditText = view.findViewById(R.id.editLoginEmail)
            val textPassword: EditText = view.findViewById(R.id.editLoginPassword)
            mPresenter.login(textEmail.text.toString().trim(), textPassword.text.toString().trim())
        })

        buttonCancel?.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        return alertDialog!!
    }

    override fun loginSuccess() {
        DataLocalManager.setLoginStatus(true)
        mIUserLoggedOutView.loadLoggedInView()
        dismiss()
    }

    override fun loginFail() {
        Toast.makeText(context, Constant.loginFail, Toast.LENGTH_LONG).show()
        dismiss()
    }

    override fun loginProgress() {
        mProgressBar?.gone()
    }

}
