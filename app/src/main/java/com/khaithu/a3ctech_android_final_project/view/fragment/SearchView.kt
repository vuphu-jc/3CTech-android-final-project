package com.khaithu.a3ctech_android_final_project.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.khaithu.a3ctech_android_final_project.R
import kotlinx.android.synthetic.main.fragment_search_view.*

class SearchView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_view, container, false)
    }
}
