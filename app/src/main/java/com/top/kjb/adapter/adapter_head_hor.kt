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
import com.top.kjb.bean.*
import com.top.kjb.customview.RoundImageView
import com.top.kjb.tabfragment.newfragmentone.coach_detail

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_head_hor : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_main_item_newdetail.bean_main_item_newdetail_coachList>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(
        context: Context,
        list: ArrayList<bean_main_item_newdetail.bean_main_item_newdetail_coachList>
    ) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layou_head_hor_item, p0, false)
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

        ImageLoader.getInstance().displayImage(mData?.get(p1)?.coachHeadImg, p0.id_head_image)
        p0.id_name_text.setText(mData?.get(p1)?.coachName)
        p0.id_big_view_click.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(mycontent, coach_detail::class.java)
                intent.putExtra("uid",mData?.get(p1)?.coachId)
                mycontent?.startActivity(intent)
            }

        })
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
        lateinit var id_head_image: RoundImageView
        lateinit var id_name_text: TextView
        var id_big_view_click: View

        constructor(itemView: View) : super(itemView) {

            id_big_view_click = itemView.findViewById(R.id.id_big_view_click)
            id_name_text = itemView.findViewById(R.id.id_name_text)
            id_head_image = itemView.findViewById(R.id.id_head_image)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}