package com.khaithu.a3ctech_android_final_project.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.adapter.HandlerPageEvent
import com.khaithu.a3ctech_android_final_project.adapter.PageAdapter
import com.khaithu.a3ctech_android_final_project.adapter.SearchRecyclerAdapter
import com.khaithu.a3ctech_android_final_project.adapter.interfaceadapter.IHandleEvent
import com.khaithu.a3ctech_android_final_project.helper.Constant
import com.khaithu.a3ctech_android_final_project.model.ResultMovie
import com.khaithu.a3ctech_android_final_project.presenter.SearchPresenter
import com.khaithu.a3ctech_android_final_project.view.MovieDetailActivity
import com.khaithu.a3ctech_android_final_project.view.interfaceView.ISearchMovieFragment
import kotlinx.android.synthetic.main.fragment_search_view.view.*

class SearchMovieFragment : Fragment(), ISearchMovieFragment, IHandleEvent, HandlerPageEvent {

    private var mSearchAdapter: SearchRecyclerAdapter? = null
    private var mView: View? = null
    private var mPageAdapter: PageAdapter? = null

    private val mPresenter: SearchPresenter = SearchPresenter(this)
    private var mList: MutableList<Int> = mutableListOf()
    private var mQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_search_view, container, false)

        mView?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mQuery = query
                    mPresenter.search(mQuery)
                }
                createRecyclerView(mView)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return mView
    }

    override fun onSearchSuccess(results: List<ResultMovie>, totalPage: Int) {
        mSearchAdapter?.updateData(results)
        mPageAdapter?.updateData(mList)
        mView?.let { onCreatePaginationRecyclerView(it, totalPage) }
        mView?.nestedScrollView?.scrollY = 0
    }

    override fun onClickEvent(id: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(Constant.intentDetailMovie, id)
        startActivity(intent)
    }

    override fun onDestroy() {
        mPresenter.onDestroyPresenter()
        mView = null
        mPageAdapter = null
        mPageAdapter = null
        super.onDestroy()
    }

    private fun createRecyclerView(view: View?) {
        mSearchAdapter = SearchRecyclerAdapter()
        mSearchAdapter?.initEvent(this)

        view?.searchRecycler?.adapter = mSearchAdapter
        view?.searchRecycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun onCreatePaginationRecyclerView(view: View, totalPage: Int) {
        for (i in 1..totalPage) {
            mList.add(i)
        }
        mPageAdapter = PageAdapter()
        mPageAdapter?.initEvent(this)
        mPageAdapter?.updateData(mList)
        view.pageRecycler.adapter = mPageAdapter
        view.pageRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun handleEvent(number: Int) {
        mPresenter.updatePage(number)
        mQuery.let { mPresenter.search(it) }
    }
}
