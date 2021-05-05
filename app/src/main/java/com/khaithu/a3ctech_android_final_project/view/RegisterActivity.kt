package com.khaithu.a3ctech_android_final_project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.User
import com.khaithu.a3ctech_android_final_project.presenter.RegisterPresenter
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IRegisterActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), IRegisterActivity {

    private val mPresenter: RegisterPresenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonRegister.setOnClickListener(View.OnClickListener {
            val user: User = User(
                editFullName.text.toString(),
                editEmail.text.toString()
            )
            val password: String = editPassword.text.toString()
            val confirmPassword : String = editConfirmPassword.text.toString()
            mPresenter.register(user, password, confirmPassword)
        })

        buttonCancel.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun onRegisterSuccess() {
        Toast.makeText(this, Constant.registerSuccess, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onRegisterFail() {
        Toast.makeText(this, Constant.registerFail, Toast.LENGTH_LONG).show()
    }
}
