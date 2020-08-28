package com.top.kjb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.bean.bean_twopage_item2
import com.top.kjb.tabfragment.fragmenttwo_view.fragmenttwo_detail_zixun

/**
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_twopage_zixun : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_twopage_item2.bean_twopage_item2_item>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(
        context: Context,
        list: ArrayList<bean_twopage_item2.bean_twopage_item2_item>
    ) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_zixun_view_item, p0, false)
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
        ImageLoader.getInstance().displayImage(bean?.pic, p0.id_image)
        p0.id_read_num.setText(bean?.readTimes + "人阅读")
        p0.id_tags.setText("#" + bean?.tags + "#")
        p0.id_text.setText(bean?.text)

        p0.id_click_zixun_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(mycontent, fragmenttwo_detail_zixun::class.java)
                intent.putExtra("id",bean?.id)
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


        lateinit var id_click_zixun_view: View
        lateinit var id_image: ImageView
        lateinit var id_read_num: TextView
        lateinit var id_tags: TextView
        lateinit var id_text: TextView

        constructor(itemView: View) : super(itemView) {
            id_click_zixun_view = itemView.findViewById<View>(R.id.id_click_zixun_detail)
            id_image = itemView.findViewById<ImageView>(R.id.id_image)
            id_read_num = itemView.findViewById<TextView>(R.id.id_read_num)
            id_tags = itemView.findViewById<TextView>(R.id.id_tags)
            id_text = itemView.findViewById<TextView>(R.id.id_text)

        }
    }
}