package com.khaithu.a3ctech_android_final_project.view.navigator

import android.content.Intent
import androidx.core.content.ContextCompat
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.view.MovieDetailActivity
import com.khaithu.a3ctech_android_final_project.view.VideoPlayerActivity
import com.khaithu.a3ctech_android_final_project.view.dialog.LoginDialog
import com.khaithu.a3ctech_android_final_project.view.navigator.navigatorinterface.IMovieDetailNavigator

class MovieDetailNavigator(var mMovieDetailActivity: MovieDetailActivity) : IMovieDetailNavigator {
    override fun goToVideoPlayerView(value: Int) {
        val intent: Intent = Intent(mMovieDetailActivity, VideoPlayerActivity::class.java)
        intent.putExtra(Constant.intentPlayVideo, value)
        ContextCompat.startActivity(mMovieDetailActivity, intent, null)
        mMovieDetailActivity.overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }

    override fun goToLoginDialog() {
        val mLoginDialog = LoginDialog()
        mLoginDialog.show(mMovieDetailActivity.supportFragmentManager, "")
    }
}
