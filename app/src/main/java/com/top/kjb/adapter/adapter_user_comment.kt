package com.top.kjb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_main_item_about_xuanliangdiann
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.customview.RoundImageView
import com.top.kjb.utils.functionClass

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_user_comment : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_main_item_about_xuanliangdiann.bean_main_item_about_xuanliangdiann_highlightsGymVOList>
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(
        context: Context,
        list: ArrayList<bean_main_item_about_xuanliangdiann.bean_main_item_about_xuanliangdiann_highlightsGymVOList>
    ) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_user_comment_item, p0, false)
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
        try {
            var bean = mData?.get(p1)

            ImageLoader.getInstance().displayImage(bean?.highlightsUser.headImg, p0.id_head_img)
            p0.id_username.setText(bean?.highlightsUser.username)
            p0.id_motto.setText(bean?.highlightsUser.motto)
            p0.id_use_commit_text.setText(bean?.highlightsComments?.commentsText)
            p0.id_time.setText(
                functionClass.getTime_ms(
                    bean.highlightsComments.startTime.toString(),
                    "yyyy-MM-dd"
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
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

        lateinit var id_head_img: RoundImageView
        lateinit var id_username: TextView
        lateinit var id_motto: TextView
        lateinit var id_time: TextView
        lateinit var id_use_commit_text: TextView
        var big_view: LinearLayout? = null

        constructor(itemView: View) : super(itemView) {
            id_use_commit_text = itemView.findViewById(R.id.id_use_commit_text)
            id_motto = itemView.findViewById(R.id.id_motto)
            id_time = itemView.findViewById(R.id.id_time)
            id_username = itemView.findViewById(R.id.id_username)
            id_head_img = itemView.findViewById(R.id.id_head_img)

//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}