package com.khaithu.a3ctech_android_final_project.view

import android.os.Bundle
import android.view.View
import com.google.android.youtube.player.*
import com.khaithu.a3ctech_android_final_project.BuildConfig
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.extension.gone
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.ResultVideo
import com.khaithu.a3ctech_android_final_project.presenter.GetVideoPresenter
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IVideoPlayerView
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : YouTubeBaseActivity(), IVideoPlayerView {

    private lateinit var onInitializedListenerPlayer: YouTubePlayer.OnInitializedListener
    private lateinit var onInitializedListenerThumbnail: YouTubeThumbnailView.OnInitializedListener
    private lateinit var result: ResultVideo
    private val mPresenter: GetVideoPresenter = GetVideoPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        val id: Int = intent.getIntExtra(Constant.intentPlayVideo, 0)
        mPresenter.getVideos(id)
    }

    override fun onVideoReceiver(resultVideos: List<ResultVideo>) {

        resultVideos.forEach { resultVideo: ResultVideo ->
            if (resultVideo.isTrailer()) {
                result = resultVideo
            }
        }

        onInitializedListenerPlayer = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                youTubePlayer: YouTubePlayer?,
                p2: Boolean
            ) {
                youTubePlayer?.loadVideo(result.key)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) = Unit
        }

        onInitializedListenerThumbnail = object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(
                view: YouTubeThumbnailView?,
                loader: YouTubeThumbnailLoader?
            ) {
                loader?.setVideo(result.key)

                loader?.setOnThumbnailLoadedListener(object :
                    YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                    override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p1: String?) {
                        loader.release()
                    }

                    override fun onThumbnailError(
                        p0: YouTubeThumbnailView?,
                        p1: YouTubeThumbnailLoader.ErrorReason?
                    ) = Unit
                })
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) = Unit
        }

        videoThumbnail.initialize(BuildConfig.YoutubeApiKey, onInitializedListenerThumbnail)

        buttonPlay.setOnClickListener(View.OnClickListener {
            videoPlayer.initialize(BuildConfig.YoutubeApiKey, onInitializedListenerPlayer)
            buttonPlay.gone()
            videoThumbnail.gone()
        })
    }

    override fun onDestroy() {
        mPresenter.onDestroyPresenter()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun onBack(view: View) {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
