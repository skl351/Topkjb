package com.top.kjb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import cc.shinichi.library.ImagePreview
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.customview.roundimage
import com.top.kjb.utils.functionClass

/**
 * 用户动态评
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_nine_image : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mInflater: LayoutInflater? = null
    var mData: List<String>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: List<String>?) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_nine_image, p0, false)
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
        p0.id_image.layoutParams.width =functionClass.getScreenWidth(mycontent!!).div(3) - functionClass.dip2px(18f, mycontent!!)
        p0.id_image.layoutParams.height =functionClass.getScreenWidth(mycontent!!).div(3) - functionClass.dip2px(18f, mycontent!!)
        ImageLoader.getInstance().displayImage(mData?.get(p1),p0.id_image)

        p0.id_image.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                ImagePreview
                    .getInstance()
                    .setContext(mycontent!!)
                    .setIndex(p1)
                    .setImageList(mData!!)
                    .setEnableDragClose(true)
                    .setEnableUpDragClose(true)
                    .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                    .setShowDownButton(false)
//                    .setBigImageLongClickListener(object : OnBigImageLongClickListener {
//                        override fun onLongClick(activity: Activity?, view: View?, position: Int): Boolean {
//                            var dialog = savepicture_bottomclick()
//                            dialog.setcontext(view?.context!!)
//                            dialog.setimagesrc(list_img_big.get(position))
//                            dialog.show((mContext as BaseActivity).supportFragmentManager, "")
//                            return true
//                        }
//
//
//                    })
                    .start()
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
        lateinit var id_image: roundimage

        constructor(itemView: View) : super(itemView) {
            id_image = itemView.findViewById(R.id.id_image)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }
}