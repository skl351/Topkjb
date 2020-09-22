package com.top.kjb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.bean.*
import com.top.kjb.customview.RoundImageView
import com.top.kjb.customview.roundimage
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_head_yuanquan : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<String>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(
        context: Context,
        list: ArrayList<String>
    ) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_hor_head2, p0, false)
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
        var lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, -10, 0)
        p0.id_line_big?.setLayoutParams(lp);

        ImageLoader.getInstance().displayImage(mData?.get(p1), p0.id_vip_head)

    }

    class ItemViewHolder : androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var tag = -1
        fun settag(tag: Int) {
            this.tag = tag
        }

        fun gettag(): Int {
            return tag
        }

        var big_view: LinearLayout? = null
        var id_vip_head: roundimage? = null
        var id_line_big: LinearLayout? = null

        constructor(itemView: View) : super(itemView) {

            id_line_big = itemView.findViewById(R.id.id_line_big)
            id_vip_head = itemView.findViewById(R.id.id_vip_head)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}