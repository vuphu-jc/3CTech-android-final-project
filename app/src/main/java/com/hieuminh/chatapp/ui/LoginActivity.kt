package com.hieuminh.chatapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.constant.Constant
import com.hieuminh.chatapp.dialog.LoadingDialog
import com.hieuminh.chatapp.presenter.impl.LoginPresenterImpl
import com.hieuminh.chatapp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity(), LoginPresenter.View {

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        btLogin.setOnClickListener {
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()
            requestLogin(email, password)
        }

        btLoginWithGoogle.setOnClickListener {
            requestLoginWithGoogle()
        }

        btLoginWithFacebook.setOnClickListener {
            requestLoginWithFacebook()
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                checkInput()
            }

        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                checkInput()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        checkInput()
    }

    override fun onLoginSuccess(userId: String) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra(Constant.USER_ID, userId)
        startActivity(intent)
    }

    override fun onLoginFail(message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.notice))
        builder.setMessage(message)
        builder.setNegativeButton(resources.getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
        builder.show()

        etPassword.setText(resources.getString(R.string.empty))
    }

    override fun onStartProcessBar(message: String) {
        loadingDialog.startLoadingDialog(message)
    }

    override fun onStopProcessBar() {
        loadingDialog.dismissDialog()
    }

    override fun requestLogin(username: String, password: String) {
        loginPresenter.login(username, password)
    }

    override fun requestLoginWithGoogle() {
        loginPresenter.loginWithGoogle()
    }

    override fun requestLoginWithFacebook() {
        loginPresenter.loginWithFacebook()
    }

    private fun init() {
        loginPresenter = LoginPresenterImpl(this, this)
        loadingDialog = LoadingDialog(this)
    }

    private fun checkInput() {
       if(etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
            btLogin.isEnabled = false
           btLogin.setTextColor(resources.getColor(R.color.btn_login_unable))

       } else {
            btLogin.isEnabled = true
           btLogin.setTextColor(resources.getColor(R.color.btn_login_enable))
       }
    }
}
