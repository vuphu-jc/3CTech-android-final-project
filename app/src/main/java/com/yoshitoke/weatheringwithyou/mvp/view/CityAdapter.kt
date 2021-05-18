package com.yoshitoke.weatheringwithyou.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City


class CityAdapter(con: Context, arrList: List<City>): BaseAdapter() {

    var list: List<City> = listOf()
    var context: Context? = null
    var myInflater: LayoutInflater? = null

    init {
        this.context    = con
        this.myInflater = LayoutInflater.from(context)
        this.list  = arrList
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val myView = myInflater!!.inflate(R.layout.simple_spinner_dropdown_item, null)
        val cityObj = list[p0]

        val full_name : String = cityObj.name
        val text1 = myView?.findViewById<CheckedTextView>(R.id.text_spinner)
        text1?.text = full_name
        return myView
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}
