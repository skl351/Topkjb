package com.top.kjb.utils

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.adapter.adapter_nine_image
import com.top.kjb.bean.bean_total_list
import com.top.kjb.tabfragment.fragmentthree_view.fragmentthree_user_center
import com.top.kjb.tabfragment.fragmenttwo_view.fragmenttwo_detail_quanzi
import com.top.kjb.tabfragment.fragmenttwo_view.fragmenttwo_detail_xuanliangdian2
import com.top.kjb.tabfragment.fragmenttwo_view.fragmenttwo_detail_zixun

object view_build {

    fun settotalview(
        idBigView: LinearLayout?,
        bean: bean_total_list.bean_total_list_class?,
        mycontent: Context?
    ) {
        if (bean?.textType == 3) {
            val son_view = View.inflate(mycontent, R.layout.layout_zixun_view_item, null)

            ImageLoader.getInstance().displayImage(bean?.pic, son_view.findViewById<ImageView>(R.id.id_image))
            son_view.findViewById<TextView>(R.id.id_read_num).setText(bean?.readTimes + "人阅读")
            son_view.findViewById<TextView>(R.id.id_tags).setText("#" + bean?.tags + "#")
            son_view.findViewById<TextView>(R.id.id_text).setText(bean?.text)

            son_view.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    var intent = Intent(mycontent, fragmenttwo_detail_zixun::class.java)
                    intent.putExtra("id",bean?.id)
                    mycontent?.startActivity(intent)
                }

            })

            idBigView?.addView(son_view)
        } else {
            val son_view = View.inflate(mycontent, R.layout.layout_two_item, null)

            son_view.findViewById<RecyclerView>(R.id.id_RecyclerView_image)?.setOnTouchListener(object :View.OnTouchListener{
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    if (p1?.action== MotionEvent.ACTION_UP){
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
            son_view.findViewById<View>(R.id.id_click_big_view)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        if (bean?.textType==1){
                            var intent = Intent(mycontent, fragmenttwo_detail_quanzi::class.java)
                            intent.putExtra("id",bean?.id)
                            mycontent?.startActivity(intent)
                        }
                        if (bean?.textType==2){
                            var intent = Intent(mycontent, fragmenttwo_detail_xuanliangdian2::class.java)
                            intent.putExtra("id",bean?.id)
                            mycontent?.startActivity(intent)
                        }
                    }

                })
            son_view.findViewById<View>(R.id.id_click_user_info).setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                                            var intent = Intent(mycontent, fragmentthree_user_center::class.java)
                        intent.putExtra("userId",bean?.userId)
                        mycontent?.startActivity(intent)
                }

            })



            val layoutmanager = GridLayoutManager(mycontent, 3)
            son_view.findViewById<RecyclerView>(R.id.id_RecyclerView_image).layoutManager =
                layoutmanager
            if ("".equals(bean?.pic)||null.equals(bean?.pic)) {
                son_view.findViewById<RecyclerView>(R.id.id_RecyclerView_image)?.visibility =
                    View.GONE
            } else {
                son_view.findViewById<RecyclerView>(R.id.id_RecyclerView_image)?.visibility =
                    View.VISIBLE
                var list_image = bean?.pic?.split(",")
                son_view.findViewById<RecyclerView>(R.id.id_RecyclerView_image)?.adapter =
                    adapter_nine_image(mycontent!!, list_image)
            }

            son_view.findViewById<TextView>(R.id.id_text).text = bean?.text
            son_view.findViewById<TextView>(R.id.id_username).text = bean?.username
            son_view.findViewById<TextView>(R.id.id_motto).text = bean?.motto
            ImageLoader.getInstance()
                .displayImage(bean?.headImg, son_view.findViewById<ImageView>(R.id.id_head_img))

            idBigView?.addView(son_view)
        }


    }
}