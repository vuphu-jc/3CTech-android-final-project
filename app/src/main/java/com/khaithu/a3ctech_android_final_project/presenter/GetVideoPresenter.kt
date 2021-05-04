package com.khaithu.a3ctech_android_final_project.presenter

import android.util.Log
import com.khaithu.a3ctech_android_final_project.BuildConfig
import com.khaithu.a3ctech_android_final_project.api.RestClient
import com.khaithu.a3ctech_android_final_project.model.Video
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.IGetVideoPresenter
import com.khaithu.a3ctech_android_final_project.service.IMovieService
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IVideoPlayerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GetVideoPresenter(var mVideoPlayerView: IVideoPlayerView) : BasePresenter(), IGetVideoPresenter {

    private val movieService: IMovieService = RestClient.getMovieService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getVideos(movieId: Int) {
        val call: Observable<Video> = movieService.getVideo(
            movieId,
            BuildConfig.TMDBApiKey,
            LanguageEnum.ENGLISH.value
        )
        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Video> {
                override fun onComplete() = Unit

                override fun onSubscribe(d: Disposable?) {
                    if (d != null) {
                        compositeDisposable.add(d)
                    }
                }

                override fun onNext(video: Video?) {
                    if (video != null) {
                        mVideoPlayerView.onVideoReceiver(video.resultVideos)
                    }
                }

                override fun onError(e: Throwable?) {
                    e?.message?.let { Log.e("error", it) }
                }
            })
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}
