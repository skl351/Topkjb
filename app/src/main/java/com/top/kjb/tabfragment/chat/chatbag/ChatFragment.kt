package com.top.kjb.tabfragment.chat.chatbag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants
import com.top.kjb.R
import com.top.kjb.originpack.BaseFragment
import kotlinx.android.synthetic.main.layout_chat_fragment_view.*

class ChatFragment : BaseFragment() {
    private var mBaseView: View? = null
    lateinit var mChatInfo: ChatInfo
    var mTitleBar: TitleBarLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBaseView = inflater.inflate(R.layout.layout_chat_fragment_view, container, false)
        return mBaseView

    }

    private fun initView() {
        //单聊组件的默认UI和交互初始化
        chat_layout.initDefault()
        /*
         * 需要聊天的基本信息
         */chat_layout.setChatInfo(mChatInfo)

        //获取单聊面板的标题栏
        //获取单聊面板的标题栏
        mTitleBar = chat_layout.getTitleBar()
        mTitleBar?.visibility = View.GONE


    }

    override fun onResume() {
        super.onResume()
        val bundle = arguments
        mChatInfo = bundle!!.getSerializable("chatInfo") as ChatInfo
        if (mChatInfo == null) {
            return
        }
        initView()

    }


}