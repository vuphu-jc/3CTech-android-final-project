package com.khaithu.a3ctech_android_final_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaithu.a3ctech_android_final_project.R
import kotlinx.android.synthetic.main.item_pagination_number.view.*

class PageAdapter :
    RecyclerView.Adapter<PageAdapter.PageNumberViewHolder>() {

    private lateinit var mHandlerPageEvent: HandlerPageEvent
    private  var list: MutableList<Int> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageNumberViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_pagination_number, parent, false)
        return PageNumberViewHolder(view, mHandlerPageEvent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PageNumberViewHolder, position: Int) {
        val pageHolder: PageNumberViewHolder = holder
        pageHolder.bind(list[position])
    }

     fun updateData(list: MutableList<Int>) {
        this.list = list
    }

    fun initEvent(handlerPageEvent: HandlerPageEvent) {
        this.mHandlerPageEvent = handlerPageEvent
    }

    class PageNumberViewHolder(itemView: View, var mHandlerPageEvent: HandlerPageEvent) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(number: Int) {
            itemView.textNumber.text = number.toString()
            itemView.setOnClickListener(View.OnClickListener {
                mHandlerPageEvent.handleEvent(number)
            })
        }
    }
}

interface HandlerPageEvent {
    fun handleEvent(number: Int)
}
