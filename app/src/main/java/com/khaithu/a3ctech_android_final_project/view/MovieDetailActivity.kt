package com.khaithu.a3ctech_android_final_project.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.model.Genre
import com.khaithu.a3ctech_android_final_project.model.MovieDetail
import com.khaithu.a3ctech_android_final_project.presenter.GetMovieDetailPresenter
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.helper.GlideHelper
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IMovieDetailView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), IMovieDetailView {


    private val mPresenter: GetMovieDetailPresenter =
        GetMovieDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val id: Int = intent.getIntExtra(Constant.intentDetailMovie, 0)
        mPresenter.getMovieDetail(id)
    }

    override fun onMovieDetailReceived(movieDetail: MovieDetail) {
        setMovieDetailInformation(movieDetail)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroyPresenter()
    }

    private fun setMovieDetailInformation(movieDetail: MovieDetail) {
        handleDisplayImage(movieDetail)

        handleDisplayTitle(movieDetail)

        rateViewDetail.setPercent((movieDetail.voteAverage * 10).toInt())

        handleDisplayTrailer(movieDetail)

        handleDisplayGenre(movieDetail)

        handleDisplayTagline(movieDetail)

        overview.text = movieDetail.overview
    }

    private fun handleDisplayImage(movieDetail: MovieDetail) {
        GlideHelper.loadImage(this, Constant.posterBaseUrl + movieDetail.posterPath, posterMovie)

        GlideHelper.loadAndBlurImage(
            this,
            Constant.backdropBaseUrl + movieDetail.backdropPath,
            posterBackground,
            5
        )
    }

    private fun handleDisplayTitle(movieDetail: MovieDetail) {
        if (movieDetail.releaseDate.isEmpty() || movieDetail.title.isEmpty()) {
            titleDetailMovie.visibility = View.GONE
        } else {
            titleDetailMovie.text = String.format(
                TITLE_DETAIL,
                movieDetail.title,
                movieDetail.releaseDate.substring(0, 4)
            )
        }
    }

    private fun handleDisplayTrailer(movieDetail: MovieDetail) {
        if (!movieDetail.video) {
            trailer.setOnClickListener(View.OnClickListener {
            })
        } else {
            trailer.visibility = View.GONE
        }
    }

    private fun handleDisplayGenre(movieDetail: MovieDetail) {
        val genreBuffer = StringBuffer("")
        if (movieDetail.genres.isNotEmpty()) {
            movieDetail.genres.forEach { genre: Genre ->
                genreBuffer.append(genre.name).append(", ")
            }
            genres.text = genreBuffer.substring(0, genreBuffer.length - 2)
        } else {
            genres.visibility = View.GONE
        }
    }

    private fun handleDisplayTagline(movieDetail: MovieDetail) {
        if (movieDetail.tagline.isNotEmpty()) {
            tagline.text = movieDetail.tagline
        } else {
            tagline.visibility = View.GONE
        }
    }

    companion object {
        private const val TITLE_DETAIL = "%s (%s)"
    }
}
