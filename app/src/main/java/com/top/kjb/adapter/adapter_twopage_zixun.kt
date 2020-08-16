package com.top.kjb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_twopage_item2
import com.top.kjb.tabfragment.fragmentone_view.fragmentone_detail

/**
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_twopage_zixun : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_twopage_item2>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_twopage_item2>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_zixun_view_item, p0, false)
        val itemViewholder = ItemViewHolder(view!!)
        return itemViewholder
    }

    override fun onBindViewHolder(p0: androidx.recyclerview.widget.RecyclerView.ViewHolder, p1: Int) {
        p0 as ItemViewHolder
        if (p0.gettag() == 1) {
            return
        } else {
            p0.settag(1)
        }


    }

    class ItemViewHolder : androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var tag = -1
        fun settag(tag: Int) {
            this.tag = tag
        }

        fun gettag(): Int {
            return tag
        }


        constructor(itemView: View) : super(itemView) {


        }
    }
}