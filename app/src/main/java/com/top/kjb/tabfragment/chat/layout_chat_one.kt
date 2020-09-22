package com.top.kjb.tabfragment.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo
import com.top.kjb.R
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.chat.chatbag.ChatActivity
import kotlinx.android.synthetic.main.layout_chat_one_view.*


class layout_chat_one :BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_chat_one_view, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化聊天面板
        conversation_layout.initDefault()
        // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
//        ConversationLayoutHelper.customizeConversation(mConversationLayout);
        conversation_layout.getConversationList()
            .setOnItemClickListener(ConversationListLayout.OnItemClickListener { view, position, conversationInfo ->
                //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                startChatActivity(conversationInfo)
            })

    }


    private fun startChatActivity(conversationInfo: ConversationInfo) {
        val chatInfo = ChatInfo()
        chatInfo.type =
            if (conversationInfo.isGroup) V2TIMConversation.V2TIM_GROUP else V2TIMConversation.V2TIM_C2C
        chatInfo.id = conversationInfo.id
        chatInfo.chatName = conversationInfo.title
        val intent = Intent(activity,
            ChatActivity::class.java
        )
        intent.putExtra("chatInfo", chatInfo)
        startActivity(intent)
    }
}