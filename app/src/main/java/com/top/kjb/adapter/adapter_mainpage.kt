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
import com.top.kjb.tabfragment.fragmentone_view.fragmentone_detail
import per.wsj.library.AndRatingBar

/**
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_mainpage : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_main_item>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_main_item>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_main_item, p0, false)
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
        ImageLoader.getInstance().displayImage(bean?.pic, p0.id_head)
        p0.id_name.setText(bean?.name)
        p0.id_summary.setText(bean?.gDescribe)
        p0.id_show_score.setText(bean?.comprehensiveScore.toString())
        p0.id_location_long.setText("距您：" + bean?.distance)
        p0.id_star_view.rating = bean?.comprehensiveScore!!
        p0.id_big_view?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (bean?.id != 0) {
                    var intent = Intent(mycontent, fragmentone_detail::class.java)
                    intent.putExtra("id", bean?.id)
                    mycontent?.startActivity(intent)
                } else {
                    var intent = Intent(mycontent, fragmentone_detail::class.java)
                    intent.putExtra("id", bean?.gymID)
                    mycontent?.startActivity(intent)
                }

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

        lateinit var id_head: ImageView
        lateinit var id_name: TextView
        lateinit var id_summary: TextView
        lateinit var id_show_score: TextView
        lateinit var id_location_long: TextView
        lateinit var id_big_view: View
        lateinit var id_star_view: AndRatingBar

        constructor(itemView: View) : super(itemView) {

            id_star_view = itemView.findViewById(R.id.id_star_view)
            id_head = itemView.findViewById(R.id.id_head)
            id_big_view = itemView.findViewById(R.id.id_big_view)
            id_name = itemView.findViewById(R.id.id_name)
            id_summary = itemView.findViewById(R.id.id_summary)
            id_show_score = itemView.findViewById(R.id.id_show_score)
            id_location_long = itemView.findViewById(R.id.id_location_long)

//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}