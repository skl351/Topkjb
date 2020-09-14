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
import com.top.kjb.bean.bean_attention_fans
import com.top.kjb.bean.bean_main_item
import com.top.kjb.customview.RoundImageView
import com.top.kjb.tabfragment.fragmentone_view.fragmentone_detail
import com.top.kjb.utils.Sp

/**
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_user_attention_fans : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_attention_fans>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_attention_fans>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_user_attention_fans, p0, false)
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

        p0.id_name.setText(bean?.nickName)
        p0.id_motto.setText(bean?.motto)
        ImageLoader.getInstance().displayImage(bean?.imgUrl, p0.id_head)
        if (bean?.mutualFollowState == 1) {
            p0.id_attent_text.setText("已关注")
            p0.id_attent_text.setTextColor(mycontent?.getColor(R.color.color_1cbe6f)!!)
            p0.id_attent_text.isSelected = true
        } else {

            p0.id_attent_text.setTextColor(mycontent?.getColor(R.color.color_a4a4a4)!!)
            p0.id_attent_text.isSelected = false
            p0.id_attent_text.setText("关注")
        }
        p0.id_attent_text.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(Sp.attent_goto)
                intent.putExtra("userid",bean?.fansID)
                mycontent?.sendBroadcast(intent)
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

        lateinit var id_head: RoundImageView
        lateinit var id_name: TextView
        lateinit var id_motto: TextView
        lateinit var id_attent_text: TextView

        constructor(itemView: View) : super(itemView) {

            id_attent_text = itemView.findViewById(R.id.id_attent_text)
            id_name = itemView.findViewById(R.id.id_name)
            id_head = itemView.findViewById(R.id.id_head)
            id_motto = itemView.findViewById(R.id.id_motto)
//            id_name=itemView.findViewById(R.id.id_name)
//            id_summary=itemView.findViewById(R.id.id_summary)
//            id_show_score=itemView.findViewById(R.id.id_show_score)
//            id_location_long=itemView.findViewById(R.id.id_location_long)

//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}