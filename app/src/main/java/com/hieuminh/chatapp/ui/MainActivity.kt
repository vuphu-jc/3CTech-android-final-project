package com.hieuminh.chatapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.constant.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (this.intent != null) {
            val userId = this.intent.getStringExtra(Constant.USER_ID)
            tvState.text = userId
        }
    }
}
