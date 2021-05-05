package com.khaithu.a3ctech_android_final_project.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.sharedprefences.DataLocalManager
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IUserView

class UserView : Fragment() {

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_user_view, container, false)

        if (DataLocalManager.getLoginStatus()) {
            loadFragment(UserLoggedInView())
        } else {
            loadFragment(UserLoggedOutView())
        }
        return mView
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.userFrame, fragment).commit()
    }
}
