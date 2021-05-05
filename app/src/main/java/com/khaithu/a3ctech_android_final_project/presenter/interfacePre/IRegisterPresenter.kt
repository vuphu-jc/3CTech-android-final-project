package com.khaithu.a3ctech_android_final_project.presenter.interfacePre

import com.khaithu.a3ctech_android_final_project.model.User

interface IRegisterPresenter {
    fun register(user : User, password : String, confirmPassword : String)
}
