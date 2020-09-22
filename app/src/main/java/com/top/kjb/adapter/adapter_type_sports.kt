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
import com.top.kjb.customview.dialog.sport_type_dialog
import com.top.kjb.customview.roundimage
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_type_sports : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    lateinit var contextdialog: sport_type_dialog
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

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_sports_type, p0, false)
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
        p0.id_big_view.layoutParams.width = functionClass.getScreenWidth(mycontent!!) / 3
        p0.id_type_text.setText(mData?.get(p1)?.sportsName)
        p0.id_big_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                var intent=Intent(Sp.select_type)
                intent.putExtra("type",mData?.get(p1)?.sportsName)
                mycontent?.sendBroadcast(intent)
                contextdialog.dismiss()
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

        lateinit var id_type_text: TextView
        lateinit var id_big_view: View

        constructor(itemView: View) : super(itemView) {

            id_type_text = itemView.findViewById(R.id.id_type_text)
            id_big_view = itemView.findViewById(R.id.id_big_view)
        }
    }
}