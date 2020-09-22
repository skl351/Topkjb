package com.top.kjb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.top.kjb.R
import com.top.kjb.bean.bean_sports_list_item
import com.top.kjb.utils.functionClass.getTime_ms
import com.top.kjb.utils.java_util

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_mysports : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_sports_list_item>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_sports_list_item>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_my_sports_item, p0, false)
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


        var str = ""
        val start_time =
            getTime_ms(mData?.get(p1)?.togetherLogs?.time.toString(), "HH:mm")
        val end_time =
            getTime_ms(mData?.get(p1)?.togetherLogs?.endTime.toString(), "HH:mm")
        var chazhi = ""
        try {
            val a: Long =
                (mData?.get(p1)?.togetherLogs?.endTime?.toLong()!! - mData?.get(p1)?.togetherLogs?.time?.toLong()!!) / 3600000
            chazhi = a.toString() + ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        str = start_time + " - " + end_time + " (" + chazhi + "小时)"

        var zhouji=java_util.getWeek(mData?.get(p1)?.togetherLogs?.time.toString())

        val right_time1 =
            getTime_ms(mData?.get(p1)?.togetherLogs?.time.toString(), "MM月dd日")
        p0.id_mysport_right.setText(right_time1+" "+zhouji)
        p0.id_mysports_time.setText(str)

    }

    class ItemViewHolder : androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var tag = -1
        fun settag(tag: Int) {
            this.tag = tag
        }

        fun gettag(): Int {
            return tag
        }

        lateinit var id_mysports_time: TextView
        lateinit var id_mysport_right: TextView
        var big_view: LinearLayout? = null

        constructor(itemView: View) : super(itemView) {

            id_mysport_right = itemView.findViewById(R.id.id_mysport_right)
            id_mysports_time = itemView.findViewById(R.id.id_mysports_time)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}