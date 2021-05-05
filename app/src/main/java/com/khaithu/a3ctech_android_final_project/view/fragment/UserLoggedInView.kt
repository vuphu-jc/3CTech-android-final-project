package com.khaithu.a3ctech_android_final_project.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.khaithu.a3ctech_android_final_project.MainActivity
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.adapter.IWatchListEvent
import com.khaithu.a3ctech_android_final_project.adapter.WatchlistRecyclerAdapter
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.MovieDetail
import com.khaithu.a3ctech_android_final_project.model.WatchlistItem
import com.khaithu.a3ctech_android_final_project.model.enum.LanguageEnum
import com.khaithu.a3ctech_android_final_project.presenter.UserPresenter
import com.khaithu.a3ctech_android_final_project.sharedprefences.DataLocalManager
import com.khaithu.a3ctech_android_final_project.view.MovieDetailActivity
import com.khaithu.a3ctech_android_final_project.view.interfaceView.IUserLoggedInView
import kotlinx.android.synthetic.main.fragment_user_logged_in_view.view.*
import kotlinx.android.synthetic.main.fragment_user_logged_out_view.view.*

class UserLoggedInView : Fragment(), IUserLoggedInView, IWatchListEvent {

    private lateinit var mView: View
    private lateinit var mWatchlistAdapter: WatchlistRecyclerAdapter
    private var list: MutableList<WatchlistItem> = arrayListOf()

    private val mPresenter = UserPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_user_logged_in_view, container, false)

        mPresenter.getTitle()
        mPresenter.createWatchlist()

        onItemToolbarSelected(mView)
        createRecyclerView(mView)

        return mView
    }

    override fun onRemoveItemWatchListSuccess(id: Int) {
        mWatchlistAdapter.removeItem(id)
    }

    override fun updateTitle(title: String) {
        mView.loggedToolBar.title = title
    }

    override fun updateWatchlist(watchlistItem: WatchlistItem?) {
        if (watchlistItem != null) {
            list.add(watchlistItem)
        }
        mWatchlistAdapter.updateData(list)
        mView.textCountWatchList.text = list.size.toString()
    }

    override fun onMovieWatchlistClicked(id: Int?) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(Constant.intentDetailMovie, id)
        startActivity(intent)
    }

    override fun onMovieWatchlistRemoved(id: Int?) {
        if (id != null) {
            mPresenter.removeWatchlist(id)
        }
    }

    private fun onItemToolbarSelected(view: View) {
        view.loggedToolBar.overflowIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_setting) }
        view.loggedToolBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
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
                        R.id.itemLogout -> {
                            FirebaseAuth.getInstance().signOut()
                            DataLocalManager.setLoginStatus(false)

                            parentFragmentManager.beginTransaction()
                                .replace(R.id.userFrame, UserLoggedOutView()).commit()
                        }
                    }
                }
                return false
            }
        })
    }

    private fun createRecyclerView(view: View) {
        mWatchlistAdapter = WatchlistRecyclerAdapter()
        mWatchlistAdapter.initEvent(this)
        view.watchlistRecycler.adapter = mWatchlistAdapter
        view.watchlistRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}
