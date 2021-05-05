package com.hieuminh.chatapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>()   {

    var mDataList : MutableList<T> = ArrayList()

    override fun getItemCount(): Int = mDataList.size

    open fun updateData(data: List<T>, isNotifyAll: Boolean = true) {
        val positionStart = mDataList.size
        mDataList.addAll(data)
        if (isNotifyAll) this.notifyItemRangeInserted(positionStart, mDataList.lastIndex)
    }
}
