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
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_main_item_newdetail
import com.top.kjb.bean.bean_order_item
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.customview.RoundImageView

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_vip_hor_head : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_main_item_newdetail.bean_main_item_newdetail_vipHeadImgList>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_main_item_newdetail.bean_main_item_newdetail_vipHeadImgList>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_vip_hor_head, p0, false)
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
        ImageLoader.getInstance().displayImage(mData?.get(p1)?.headImg,p0.id_vip_head)

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
        var id_vip_head: RoundImageView? = null

        constructor(itemView: View) : super(itemView) {

            id_vip_head = itemView.findViewById(R.id.id_vip_head)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}