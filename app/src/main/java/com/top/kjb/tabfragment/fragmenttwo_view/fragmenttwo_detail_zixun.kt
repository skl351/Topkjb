package com.top.kjb.tabfragment.fragmenttwo_view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.adapter.adapter_nine_image
import com.top.kjb.adapter.adapter_user_comment2
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.bean.bean_user_comment
import com.top.kjb.customview.RoundImageView
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util
import kotlinx.android.synthetic.main.layout_fragmenttwo_detail_zixun.*
import retrofit2.Call
import retrofit2.Response


class fragmenttwo_detail_zixun : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmenttwo_detail_zixun)
        registerBoradcastReceiver()
        init_intent()
        init_view()
        init_refre()
        init_click()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.huifuintent)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var parentId = 0
    var replyType = 0
    var commentsId = 0
    var sendtype = 1//1评论 2回复
    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.huifuintent -> {
                    parentId = intent.getIntExtra("parentId", 0)
                    replyType = intent.getIntExtra("replyType", 0)
                    commentsId = intent.getIntExtra("commentsId", 0)
                    sendtype = 2
                    id_edit.requestFocus()
                    id_edit.setText("")
                    id_edit.setHint("回复")
                    java_util().showInput(id_edit, this@fragmenttwo_detail_zixun)
                }

            }
        }
    }

    lateinit var list_head: ArrayList<RoundImageView>
    lateinit var list_head_big: ArrayList<View>
    override fun init_view() {
        super.init_view()

        list_head_big = ArrayList<View>()
        list_head_big.add(id_head_1)
        list_head_big.add(id_head_2)
        list_head_big.add(id_head_3)
        list_head_big.add(id_head_4)
        list_head_big.add(id_head_5)
        list_head_big.add(id_head_6)
        list_head = ArrayList()
        list_head.add(id_image1)
        list_head.add(id_image2)
        list_head.add(id_image3)
        list_head.add(id_image4)
        list_head.add(id_image5)
        list_head.add(id_image6)

        id_edit.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                //获取当前界面可视部分
                window.decorView.getWindowVisibleDisplayFrame(r)
                //获取屏幕的高度
                val screenHeight = window.decorView.rootView.height
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                val heightDifference = screenHeight - r.bottom
                Log.d("Keyboard Size", "Size: " + heightDifference)
                if (heightDifference < 200) {
                    id_bottom_edit_big?.visibility = View.GONE
                    zhezhao?.visibility = View.GONE
                } else {
                    id_bottom_edit_big?.visibility = View.VISIBLE
                    zhezhao?.visibility = View.VISIBLE
                }
            }

        })

        id_edit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (sendtype == 1) {
                        init_send()
                    } else {
                        init_huifu()
                    }
                }
                return false;
            }

        })
    }

    private fun init_huifu() {
        var text = id_edit.text.toString()
        twoModel.informationReplyinsertInformationReply(
            functionClass.getToken(),
            parentId,
            text,
            replyType,
            commentsId
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        Show_toast.showText(this@fragmenttwo_detail_zixun, bean?.result)
                        init_list(id)
                        java_util().hideInput(this@fragmenttwo_detail_zixun)
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_zixun, "炫亮点评论list失败")
                    }

                }

            })
    }

    private fun init_send() {
        var text = id_edit.text.toString()
        twoModel.informationCommentsinsertInformationComments(
            functionClass.getToken(),
            functionClass.getUserId(),
            id,
            text
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        Show_toast.showText(this@fragmenttwo_detail_zixun, bean?.result)
                        init_list(id)
                        java_util().hideInput(this@fragmenttwo_detail_zixun)
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_zixun, "圈子评论list失败")
                    }

                }

            })
    }

    override fun init_intent() {
        super.init_intent()
        id = intent.getIntExtra("id", 0)
    }

    lateinit var adapter_nine: adapter_nine_image
    lateinit var list: ArrayList<bean_user_comment.bean_user_comment>
    lateinit var adapter_comment: adapter_user_comment2
    private fun init_data() {
        init_zixun()
    }


    var id = 0
    var collect_check = 0
    var like_check = 0
    private fun init_zixun() {
        twoModel.informationselectInformationById(
            functionClass.getToken(), id
        ).enqueue(object :
            retrofit2.Callback<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>> {
            override fun onFailure(
                call: Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>,
                t: Throwable
            ) {
                println("失败" + t.toString())
            }

            override fun onResponse(
                call: Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>,
                response: Response<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>
            ) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    id_text.setText(bean?.result?.text)
                    var pic = bean?.result?.pic
                    ImageLoader.getInstance().displayImage(pic, id_big_img)
                    id_time.setText(
                        functionClass.getTime_ms(
                            bean?.result?.startTime.toString(),
                            "yyyy-MM-dd hh:mm"
                        )
                    )
                    top_text.setText(bean?.result?.title)
                    id_readTimes.text = bean?.result?.readTimes + "人阅读"
                    if (bean?.result?.likeStatus!!) {
                        like_check = 1
                    } else {
                        like_check = 0
                    }
                    if (bean?.result?.favoriteStatus!!) {
                        collect_check = 1
                    } else {
                        collect_check = 0
                    }
                    if (!bean?.result?.favoriteStatus!!) {
                        id_collect_img.setImageResource(R.mipmap.icon_star_off)
                    } else {
                        id_collect_img.setImageResource(R.mipmap.icon_star_on)
                    }
                    if (!bean?.result?.likeStatus!!) {
                        id_like_image.setImageResource(R.mipmap.icon_love_off)
                    } else {
                        id_like_image.setImageResource(R.mipmap.icon_love_on)
                    }
                    id_dianzanshu.text = bean?.result.likeCounts + "人点赞"
                    var listhead = bean?.result?.likeHeadImg
                    for (i in 0..listhead?.size!! - 1) {
                        list_head_big.get(i).visibility = View.VISIBLE
                        ImageLoader.getInstance()
                            .displayImage(listhead.get(i), list_head.get(i))
                    }

                } else {
                    Show_toast.showText(this@fragmenttwo_detail_zixun, "获取咨询详情")
                }


            }

        })
        init_list(id)
    }

    var currentpage = 1
    var pagesize = 10
    val twoModel: TwoModel by lazy { TwoModel() }
    private fun init_list(id: Int) {


        twoModel.informationCommentsselectComments(
            functionClass.getToken(),
            id,
            currentpage,
            pagesize
        )
            .enqueue(object : retrofit2.Callback<Result<bean_user_comment>> {
                override fun onFailure(call: Call<Result<bean_user_comment>>, t: Throwable) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_user_comment>>,
                    response: Response<Result<bean_user_comment>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list = bean?.result?.list!!
                        adapter_comment = adapter_user_comment2(this@fragmenttwo_detail_zixun, list)
                        adapter_comment.textType = 3
                        id_RecyclerView.adapter = adapter_comment
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_zixun, "咨询评论list失败")
                    }


                }

            })

    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
        id_click_commom.setOnClickListener(this)
        zhezhao.setOnClickListener(this)
        id_like_image_big.setOnClickListener(this)
        id_collect_img_big.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_like_image_big -> {
                id_like_image_big.isEnabled = false
                gotozan()
            }
            R.id.id_collect_img_big -> {
                id_collect_img_big.isEnabled = false
                gotocollect()
            }
            R.id.zhezhao -> {
                java_util().hideInput(this)
            }
            R.id.id_click_commom -> {
                id_edit.requestFocus()
                id_edit.setText("")
                java_util().showInput(id_edit, this)
            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }

    var type_send = 0//0是作品1是评论2是回复
    private fun gotozan() {
        var goint = 0
        if (like_check == 1) {
            goint = 0
        } else {
            goint = 1
        }
        twoModel.informationLikesinsertInformationLikes(
            functionClass.getToken(),
            id,
            0,
            goint,
            collect_check
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {
                id_like_image_big.isEnabled = true
            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (like_check == 1) {
                        like_check = 0
                        id_like_image.setImageResource(R.mipmap.icon_love_off)
                    } else {
                        like_check = 1
                        id_like_image.setImageResource(R.mipmap.icon_love_on)
                    }

                } else {
                    Show_toast.showText(this@fragmenttwo_detail_zixun, "操作失败")
                }
                id_like_image_big.isEnabled = true
            }

        })
    }

    private fun gotocollect() {
        var goint = 0
        if (collect_check == 1) {
            goint = 0
        } else {
            goint = 1
        }
        twoModel.informationLikesinsertInformationLikes(
            functionClass.getToken(),
            id,
            0,
            like_check,
            goint
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {
                id_collect_img_big.isEnabled = true
            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (collect_check == 1) {
                        collect_check = 0
                        id_collect_img.setImageResource(R.mipmap.icon_star_off)
                    } else {
                        collect_check = 1
                        id_collect_img.setImageResource(R.mipmap.icon_star_on)
                    }

                } else {
                    Show_toast.showText(this@fragmenttwo_detail_zixun, "操作失败")
                }
                id_collect_img_big.isEnabled = true
            }

        })
    }
}