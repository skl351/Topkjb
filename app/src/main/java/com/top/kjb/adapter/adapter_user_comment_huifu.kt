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
import com.lxj.xpopup.XPopup
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_user_comment
import com.top.kjb.customview.RoundImageView
import com.top.kjb.customview.commentjubao_bottom
import com.top.kjb.model.TwoModel
import com.top.kjb.tabfragment.fragmentthree_view.fragmentthree_user_center
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import retrofit2.Call
import retrofit2.Response

/**
 * 用户普通评论
 * Created by MaiBenBen on 2019/1/22.
 */
class adapter_user_comment_huifu : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mainid=0
    var mInflater: LayoutInflater? = null
    var mData: ArrayList<bean_user_comment.bean_user_comment>? = null
    override fun getItemCount(): Int {
        return mData?.size!!
    }

    var textType = 1//1是圈子 2是炫亮点 3 咨询
    override fun getItemViewType(position: Int): Int {
        return position
    }

    var mycontent: Context? = null

    constructor(context: Context, list: ArrayList<bean_user_comment.bean_user_comment>) : super() {
        mData = list
        mInflater = LayoutInflater.from(context)
        mycontent = context
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = mInflater?.inflate(R.layout.layout_user_commenthuifu_item, p0, false)
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
        ImageLoader.getInstance().displayImage(bean?.headImg, p0.id_head_img)
        p0.id_head_img.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(mycontent, fragmentthree_user_center::class.java)
                intent.putExtra("userId", bean?.responderId)
                mycontent?.startActivity(intent)

            }

        })
        p0.id_username.setText(bean?.username)
        if (!"".equals(bean?.parentUserName)&&null!=bean?.parentUserName){
            p0.id_huifu_flag.visibility=View.VISIBLE
            p0.id_huifu_username.visibility=View.VISIBLE
            p0.id_huifu_username.setText(bean?.parentUserName)
        }else{
            p0.id_huifu_flag.visibility=View.GONE
            p0.id_huifu_username.visibility=View.GONE
        }
        p0.id_use_commit_text.setText(bean?.replyText)
        p0.id_love_num.setText(bean?.likesTimes)
        p0.id_time.setText(functionClass.getTime_ms(bean?.startTime.toString(), "yyyy-MM-dd HH:mm"))
        var likeStatus = bean?.likeStatus
        if (likeStatus!!) {
            p0.id_zan_img.isSelected = true
        } else {
            p0.id_zan_img.isSelected = false
        }
        p0.id_click_zan.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                when(textType){
                    1->{
                        gotozanquanzi(p0.id_zan_img,bean?.id!!,p0.id_love_num)
                    }
                    2->{
                        gotozan(p0.id_zan_img,bean?.id!!,p0.id_love_num)
                    }
                    1->{
                        gotozan_zixun(p0.id_zan_img,bean?.id!!,p0.id_love_num)
                    }
                }

            }

        })
        p0.id_comment_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
//隐藏
                var intent=Intent(Sp.huifudimess)
                mycontent?.sendBroadcast(intent)
                var bottom = commentjubao_bottom(mycontent!!)
                bottom.setbean(bean)
                bottom.setmaind(mainid)
                bottom.setreplyType(1)
                XPopup.Builder(mycontent)
                    .hasShadowBg(true)
                    .atView(p0)
                    .asCustom(bottom)
                    .show()

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
        lateinit var id_head_img: RoundImageView
        lateinit var id_username: TextView
        lateinit var id_time: TextView
        lateinit var id_use_commit_text: TextView
        lateinit var id_love_num: TextView
        lateinit var id_huifu_username: TextView
        lateinit var id_click_zan: View
        lateinit var id_zan_img: ImageView
        lateinit var id_comment_view: View
        lateinit var id_huifu_flag: View

        constructor(itemView: View) : super(itemView) {

            id_huifu_username = itemView.findViewById(R.id.id_huifu_username)
            id_huifu_flag = itemView.findViewById(R.id.id_huifu_flag)
            id_comment_view = itemView.findViewById(R.id.id_comment_view)
            id_zan_img = itemView.findViewById(R.id.id_zan_img)
            id_click_zan = itemView.findViewById(R.id.id_click_zan)
            id_love_num = itemView.findViewById(R.id.id_love_num)
            id_use_commit_text = itemView.findViewById(R.id.id_use_commit_text)
            id_time = itemView.findViewById(R.id.id_time)
            id_username = itemView.findViewById(R.id.id_username)
            id_head_img = itemView.findViewById(R.id.id_head_img)
//            big_view = itemView.findViewById(R.id.big_view)
        }
    }

    val twoModel: TwoModel by lazy { TwoModel() }
    //点赞圈子
    private fun gotozanquanzi(idZanImg: ImageView, id: Int,       idLoveNum: TextView) {
        var zanstatus = 0
        if (idZanImg.isSelected) {
            zanstatus = 0
        } else {
            zanstatus = 1
        }

        twoModel.circleLikesinsertCircleLikes(
            functionClass.getToken(),
            id,
            1,
            zanstatus,
            0
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (idZanImg.isSelected) {
                        idZanImg.isSelected = false
                        try {
                            var zan = idLoveNum.text.toString().toInt()
                            zan--
                            idLoveNum.text = zan.toString()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        idZanImg.isSelected = true
                        try {
                            var zan = idLoveNum.text.toString().toInt()
                            zan++
                            idLoveNum.text = zan.toString()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                } else {
                    Show_toast.showText(mycontent, "操作失败")
                }
            }

        })
    }
    //点赞炫亮点
    private fun gotozan(
        idZanImg: ImageView,
        id: Int,
        idLoveNum: TextView
    ) {
        var zanstatus = 0
        if (idZanImg.isSelected) {
            zanstatus = 0
        } else {
            zanstatus = 1
        }

        twoModel.highlightsLikesinsertHighlightsLikes(
            functionClass.getToken(),
            id,
            1,
            zanstatus,
            0
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (idZanImg.isSelected) {
                        idZanImg.isSelected = false
                        try {
                            var zan = idLoveNum.text.toString().toInt()
                            zan--
                            idLoveNum.text = zan.toString()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        idZanImg.isSelected = true
                        try {
                            var zan = idLoveNum.text.toString().toInt()
                            zan++
                            idLoveNum.text = zan.toString()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                } else {
                    Show_toast.showText(mycontent, "操作失败")
                }
            }

        })
    }
    private fun gotozan_zixun(
        idZanImg: ImageView,
        id: Int,
        idLoveNum: TextView
    ) {
        var zanstatus = 0
        if (idZanImg.isSelected) {
            zanstatus = 0
        } else {
            zanstatus = 1
        }

        twoModel.informationLikesinsertInformationLikes(
            functionClass.getToken(),
            id,
            1,
            zanstatus,
            0
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (idZanImg.isSelected) {
                        idZanImg.isSelected = false
                        try {
                            var zan = idLoveNum.text.toString().toInt()
                            zan--
                            idLoveNum.text = zan.toString()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        idZanImg.isSelected = true
                        try {
                            var zan = idLoveNum.text.toString().toInt()
                            zan++
                            idLoveNum.text = zan.toString()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                } else {
                    Show_toast.showText(mycontent, "操作失败")
                }
            }

        })
    }
}