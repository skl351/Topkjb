package com.top.kjb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.top.kjb.R
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_order_item
import com.top.kjb.bean.bean_search_item
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Sp
import org.w3c.dom.Text

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_searchlist : RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        val view = mInflater?.inflate(R.layout.layout_search_item, p0, false)
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
        p0.id_place_summary.setText(mData?.get(p1)?.address.toString())
        p0.id_place_name.setText(mData?.get(p1)?.name.toString())
        p0.id_big_view?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(Sp.searchback)
                intent.putExtra("bean_search_item", mData?.get(p1))
                intent.putExtra("list",mData?.get(p1)?.businessHours)
                mycontent?.sendBroadcast(intent)
                (mycontent as BaseActivity).finish()
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

        var id_big_view: LinearLayout? = null
        lateinit var id_place_name: TextView
        lateinit var id_place_summary: TextView

        constructor(itemView: View) : super(itemView) {

            id_place_summary = itemView.findViewById(R.id.id_place_summary)
            id_place_name = itemView.findViewById(R.id.id_place_name)
            id_big_view = itemView.findViewById(R.id.id_big_view)
        }
    }
}