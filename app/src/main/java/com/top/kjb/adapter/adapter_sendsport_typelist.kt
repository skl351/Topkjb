package com.top.kjb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.top.kjb.R
import com.top.kjb.bean.bean_type_item

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_sendsport_typelist : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_type_item>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_type_item>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_sendsport_type_item, p0, false)
        val itemViewholder = ItemViewHolder(view!!)
        return itemViewholder
    }

    override fun onBindViewHolder(
        p0: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        p1: Int
    ) {
        p0 as ItemViewHolder
        if (p1 == getSelectPostion()) {
            p0.id_type_name?.setBackgroundColor(mycontent?.resources?.getColor(R.color.bg_color)!!)
            p0.id_type_name?.setTextColor(mycontent?.resources?.getColor(R.color.white)!!)
        } else {
            p0.id_type_name?.setBackgroundColor(mycontent?.resources?.getColor(R.color.color_F9FAFB)!!)
            p0.id_type_name?.setTextColor(mycontent?.resources?.getColor(R.color.color_333333)!!)
        }
        p0.id_type_name?.setText(mData?.get(p1)?.sportsName)
        p0.id_type_name?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(vie: View?) {
                setSelect(p0.getLayoutPosition())
            }

        })
    }

    private var postion = 0
    fun getSelectPostion(): Int {
        return postion
    }


    fun setSelect(postion: Int) {
        this.postion = postion
        notifyDataSetChanged()
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
        var id_type_name: TextView? = null

        constructor(itemView: View) : super(itemView) {

            id_type_name = itemView.findViewById(R.id.id_type_name)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}