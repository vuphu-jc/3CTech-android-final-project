package com.hieuminh.chatapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.constant.Constant
import com.hieuminh.chatapp.dialog.CustomAlertDialog
import com.hieuminh.chatapp.dialog.LoadingDialog
import com.hieuminh.chatapp.presenter.contract.LoginContract
import com.hieuminh.chatapp.presenter.impl.LoginPresenterImpl
import com.hieuminh.chatapp.ui.navigator.Impl.LoginNavigatorImpl
import com.hieuminh.chatapp.ui.navigator.LoginNavigator
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var mLoginNavigator: LoginNavigator
    private lateinit var mLoginPresenter: LoginContract.Presenter
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var mCustomAlertDialog: CustomAlertDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        etEmail.addTextChangedListener(onHandlerTextEmailChangeListener())

        etPassword.addTextChangedListener(onHandlerTextPasswordChangeListener())
    }

    override fun onStart() {
        super.onStart()
        checkInput()
        onUpdateUI(mAuth.currentUser)
    }

    override fun onDestroy() {
        mLoginPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.GoogleKey.RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            account?.idToken?.let { mLoginPresenter.firebaseAuthWithGoogle(it) }
        }
    }

    override fun onLoginSuccess() {
        mLoginNavigator.startMainActivity()
    }

    override fun onLoginFail(message: String?) {
        mCustomAlertDialog.startAlertDialog(message)
        etPassword.setText(resources.getString(R.string.empty))
    }

    override fun onLoginWithGoogleFailure() {
        mCustomAlertDialog.startAlertDialog(resources.getString(R.string.login_with_google_is_not_available))
    }

    override fun onLoginWithFacebookFailure() {
        mCustomAlertDialog.startAlertDialog(resources.getString(R.string.login_with_facebook_is_not_available))
    }

    override fun onStartProcessBar() {
        mLoadingDialog.startLoadingDialog(resources.getString(R.string.login_loading))
    }

    override fun onStopProcessBar() {
        mLoadingDialog.dismissDialog()
    }

    override fun onUpdateUI(currentUser: FirebaseUser?) {
        currentUser?.let { mLoginNavigator.startMainActivity() }
    }

    fun onLogin(view: View) {
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()
        mLoginPresenter.login(email, password)
    }

    fun onLoginWithGoogle(view: View) {
        mLoginNavigator.startGoogleSignInClient(mGoogleSignInClient)
    }

    fun onLoginWithFacebook(view: View) {
        mLoginPresenter.loginWithFacebook()
    }

    fun onChangeStatePassword(view: View) {
        when (etPassword.transformationMethod) {
            HideReturnsTransformationMethod.getInstance() -> {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                ivStatePassword.background = ContextCompat.getDrawable(this, R.drawable.hide_pwd)
            }
            PasswordTransformationMethod.getInstance() -> {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                ivStatePassword.background = ContextCompat.getDrawable(this, R.drawable.show_pwd)
            }
        }
    }

    private fun onHandlerTextPasswordChangeListener(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            checkInput()
        }
    }

    private fun onHandlerTextEmailChangeListener(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            checkInput()
        }
    }

    private fun checkInput() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        ivStatePassword.visibility = if (password.isEmpty()) View.GONE else View.VISIBLE
        updateLoginButton(password.isNotEmpty() && email.isNotEmpty())
    }

    private fun updateLoginButton(isEnable: Boolean) {
        btLogin.isEnabled = isEnable
        val eButtonColor =
            if (isEnable) ContextCompat.getColor(this, R.color.btn_login_enable)
            else ContextCompat.getColor(this, R.color.btn_login_unable)
        btLogin.setTextColor(eButtonColor)
    }

    private fun init() {
        mLoginPresenter = LoginPresenterImpl(this)
        mLoadingDialog = LoadingDialog(this)
        mCustomAlertDialog = CustomAlertDialog(this)
        mAuth = FirebaseAuth.getInstance()
        mLoginNavigator = LoginNavigatorImpl(this)

        val googleSignOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignOption)
    }
}
