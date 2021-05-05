package com.khaithu.a3ctech_android_final_project.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.sharedprefences.DataLocalManager
import com.khaithu.a3ctech_android_final_project.view.dialog.LoginDialog
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IUserLoggedOutView
import kotlinx.android.synthetic.main.fragment_user_logged_out_view.view.*
import kotlinx.android.synthetic.main.fragment_user_view.*
import kotlinx.android.synthetic.main.fragment_user_view.view.*
import kotlinx.android.synthetic.main.login_dialog.*
import kotlinx.android.synthetic.main.login_dialog.view.*

class UserLoggedOutView : Fragment(), IUserLoggedOutView, View.OnClickListener {

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_user_logged_out_view, container, false)

        mView.buttonSignInSignOut.setOnClickListener(this)

        onItemToolbarSelected(mView)

        return mView
    }

    @SuppressLint("ResourceType")
    override fun onClick(view: View?) {
        val mLoginDialog : LoginDialog = LoginDialog(this)
        mLoginDialog.show(childFragmentManager, "")
    }

    private fun onItemToolbarSelected(view: View) {
        view.toolBar.overflowIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_setting) }
        view.toolBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item != null) {
                    when (item.itemId) {
                        R.id.itemEnglish -> {
                            DataLocalManager.setLanguage(LanguageEnum.ENGLISH)
                            return true
                        }
                        R.id.itemVietnam -> {
                            DataLocalManager.setLanguage(LanguageEnum.VIETNAM)
                            return true
                        }
                    }
                }
                return false
            }
        })
    }

    override fun loadLoggedInView() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.userFrame, UserLoggedInView()).commit()
    }
}
