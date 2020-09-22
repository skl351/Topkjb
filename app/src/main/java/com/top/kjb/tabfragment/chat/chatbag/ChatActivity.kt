package com.top.kjb.tabfragment.chat.chatbag

import android.os.Bundle
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity

class ChatActivity : BaseActivity(){
    var mChatFragment: ChatFragment? = null
    private var mChatInfo: ChatInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_chatactivity_view)
        mChatFragment = ChatFragment()

        val bundle = intent.extras
        mChatInfo = bundle.getSerializable("chatInfo") as ChatInfo

        mChatFragment = ChatFragment()
        mChatFragment!!.arguments = bundle
        supportFragmentManager.beginTransaction()
            .add(R.id.empty_view, mChatFragment!!)
            .commit()
    }
}