package com.top.kjb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.Userabout.LoginActivity
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.customview.RoundImageView
import com.top.kjb.tabfragment.fragmentthree_view.fragmentthree_user_center
import com.top.kjb.tabfragment.fragmenttwo_view.fragmenttwo_detail_quanzi
import com.top.kjb.tabfragment.fragmenttwo_view.fragmenttwo_detail_xuanliangdian2
import com.top.kjb.utils.functionClass

/**
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_twopage : RecyclerView.Adapter<RecyclerView.ViewHolder> {


    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(
        context: Context,
        list: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    ) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_two_item, p0, false)
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
        p0.id_RecyclerView_image?.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if (!functionClass.islogin()) {
                    var intent = Intent(mycontent, LoginActivity::class.java)
                    mycontent?.startActivity(intent)
                    return true
                }
                if (p1?.action==MotionEvent.ACTION_UP){
                    if (bean?.textType==1){
                        var intent = Intent(mycontent, fragmenttwo_detail_quanzi::class.java)
                        intent.putExtra("id",bean?.id)
                        mycontent?.startActivity(intent)
                        return true
                    }
                    if (bean?.textType==2){
                        var intent = Intent(mycontent, fragmenttwo_detail_xuanliangdian2::class.java)
                        intent.putExtra("id",bean?.id)
                        mycontent?.startActivity(intent)
                        return true
                    }
                }
                return true
            }

        })
        p0.big_view?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!functionClass.islogin()) {
                    var intent = Intent(mycontent, LoginActivity::class.java)
                    mycontent?.startActivity(intent)
                    return
                }
                if (bean?.textType==1){
                    var intent = Intent(mycontent, fragmenttwo_detail_quanzi::class.java)
                    intent.putExtra("id",bean?.id)
                    mycontent?.startActivity(intent)
                    return
                }
                if (bean?.textType==2){
                    var intent = Intent(mycontent, fragmenttwo_detail_xuanliangdian2::class.java)
                    intent.putExtra("id",bean?.id)
                    mycontent?.startActivity(intent)
                    return
                }
            }

        })
        p0.id_click_user_info.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (!functionClass.islogin()) {
                    var intent = Intent(mycontent, LoginActivity::class.java)
                    mycontent?.startActivity(intent)
                    return
                }
                var intent = Intent(mycontent, fragmentthree_user_center::class.java)
                intent.putExtra("userId",bean?.userId)
                mycontent?.startActivity(intent)
            }

        })


        val layoutmanager = GridLayoutManager(mycontent, 3)
        p0.id_RecyclerView_image?.layoutManager = layoutmanager
        if ("".equals(bean?.pic)){
            p0.id_RecyclerView_image?.visibility=View.GONE
        }else{
            p0.id_RecyclerView_image?.visibility=View.VISIBLE
            var list_image = bean?.pic?.split(",")
            p0.id_RecyclerView_image?.adapter = adapter_nine_image(mycontent!!, list_image)
        }

        if ("".equals(bean?.text)){
            p0.id_text.visibility=View.GONE
        }else{
            p0.id_text.visibility=View.VISIBLE
            p0.id_text.text = bean?.text
        }

        p0.id_username.setText(bean?.username)
        p0.id_motto.setText(bean?.motto)
        ImageLoader.getInstance().displayImage(bean?.headImg, p0.id_head_img)


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
        var id_RecyclerView_image: RecyclerView? = null
        lateinit var id_text: TextView
        lateinit var id_username: TextView
        lateinit var id_motto: TextView
        lateinit var id_head_img: RoundImageView
        lateinit var id_click_user_info: View
        lateinit var id_RecyclerView_image_big: View

        constructor(itemView: View) : super(itemView) {

            id_RecyclerView_image_big = itemView.findViewById(R.id.id_RecyclerView_image_big)
            id_click_user_info = itemView.findViewById(R.id.id_click_user_info)
            id_RecyclerView_image = itemView.findViewById(R.id.id_RecyclerView_image)
            big_view = itemView.findViewById(R.id.id_click_big_view)
            id_username = itemView.findViewById(R.id.id_username)
            id_motto = itemView.findViewById(R.id.id_motto)
            id_head_img = itemView.findViewById(R.id.id_head_img)
            id_text = itemView.findViewById(R.id.id_text)
        }
    }
}