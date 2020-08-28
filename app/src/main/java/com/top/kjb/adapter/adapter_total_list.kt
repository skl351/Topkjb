package com.top.kjb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.top.kjb.R
import com.top.kjb.bean.bean_total_list
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.utils.view_build

/**
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_total_list : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_total_list.bean_total_list_class>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(
        context: Context,
        list: ArrayList<bean_total_list.bean_total_list_class>
    ) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_total_list_view_item, p0, false)
        val itemViewholder = ItemViewHolder(view!!)
        return itemViewholder
    }

    override fun onBindViewHolder(
        p0: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        p1: Int
    ) {
        p0 as ItemViewHolder
        if (p0.gettag() == 1) {
            return
        } else {
            p0.settag(1)
        }
        var bean = mData?.get(p1)

        view_build.settotalview(p0.id_big_view,bean,mycontent)

    }

    class ItemViewHolder : androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var tag = -1
        fun settag(tag: Int) {
            this.tag = tag
        }

        fun gettag(): Int {
            return tag
        }

        var id_big_view: LinearLayout? = null


        constructor(itemView: View) : super(itemView) {
            id_big_view = itemView.findViewById(R.id.id_big_view)

        }
    }
}