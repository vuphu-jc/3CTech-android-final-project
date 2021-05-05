package com.khaithu.a3ctech_android_final_project.presenter

import android.util.Log
import com.khaithu.a3ctech_android_final_project.BuildConfig
import com.khaithu.a3ctech_android_final_project.api.RestClient
import com.khaithu.a3ctech_android_final_project.model.PageMovie
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.IGetMoviePresenter
import com.khaithu.a3ctech_android_final_project.service.IMovieService
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.sharedprefences.DataLocalManager
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IHomeView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GetMoviePresenter(var iHomeView: IHomeView) : BasePresenter(), IGetMoviePresenter {

    private val movieService: IMovieService = RestClient.getMovieService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getMovies() {
        val call: Observable<PageMovie> = movieService.getMovies(
            Constant.popular,
            BuildConfig.TMDBApiKey,
            DataLocalManager.getLanguage(),
            1
        )
        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PageMovie> {
                override fun onComplete() = Unit

                override fun onSubscribe(d: Disposable?) {
                    if (d != null) {
                        compositeDisposable.add(d)
                    }
                }

                override fun onNext(pageMovie: PageMovie?) {
                    if (pageMovie != null) {
                        iHomeView.onMoviesReceived(pageMovie.result)
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
