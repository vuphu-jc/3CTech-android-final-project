package com.khaithu.a3ctech_android_final_project.presenter

import android.util.Log
import com.khaithu.a3ctech_android_final_project.BuildConfig
import com.khaithu.a3ctech_android_final_project.api.RestClient
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.Search
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.presenter.interfacePre.ISearchPresenter
import com.khaithu.a3ctech_android_final_project.service.IMovieService
import com.khaithu.a3ctech_android_final_project.view.interfaceView.ISearchMovieFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchPresenter(var mView: ISearchMovieFragment?) : BasePresenter(), ISearchPresenter {

    private val movieService: IMovieService = RestClient.getMovieService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mPage: Int = 1

    override fun search(query: String) {
        val call: Observable<Search> = movieService.search(
            Constant.movie,
            BuildConfig.TMDBApiKey,
            LanguageEnum.ENGLISH.value,
            query,
            mPage,
            false
        )

        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Search> {
                override fun onComplete() = Unit

                override fun onSubscribe(d: Disposable?) {
                    if (d != null) {
                        compositeDisposable.add(d)
                    }
                }

                override fun onNext(t: Search?) {
                    if (t != null) {
                        mView?.onSearchSuccess(t.results, t.totalPage)
                    }
                }

                override fun onError(e: Throwable?) {
                    e?.message?.let { Log.e("error", it) }
                }
            })
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
        mView = null
    }

    fun updatePage(page: Int) {
        mPage = page
    }
}
