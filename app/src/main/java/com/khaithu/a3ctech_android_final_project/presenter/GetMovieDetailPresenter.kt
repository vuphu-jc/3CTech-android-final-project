package com.khaithu.a3ctech_android_final_project.presenter

import android.util.Log
import com.khaithu.a3ctech_android_final_project.BuildConfig
import com.khaithu.a3ctech_android_final_project.api.RestClient
import com.khaithu.a3ctech_android_final_project.model.MovieDetail
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.IGetMovieDetailPresenter
import com.khaithu.a3ctech_android_final_project.service.IMovieService
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IMovieDetailView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GetMovieDetailPresenter(var iMovieDetailView: IMovieDetailView) : BasePresenter(), IGetMovieDetailPresenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val movieService: IMovieService = RestClient.getMovieService()

    override fun getMovieDetail(movieId: Int) {
        val call: Observable<MovieDetail> = movieService.getMovieDetail(
            movieId,
            BuildConfig.TMDBApiKey,
            // TODO add feature language later.
            LanguageEnum.VIETNAM.value
        )
        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MovieDetail> {
                override fun onComplete() = Unit

                override fun onSubscribe(d: Disposable?) {
                    if (d != null) {
                        compositeDisposable.add(d)
                    }
                }

                override fun onNext(movieDetail: MovieDetail?) {
                    if (movieDetail != null) {
                        iMovieDetailView.onMovieDetailReceived(movieDetail)
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
