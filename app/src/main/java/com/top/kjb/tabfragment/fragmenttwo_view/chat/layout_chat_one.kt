package com.top.kjb.tabfragment.fragmenttwo_view.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.ui.EaseConversationListFragment
import com.top.kjb.R
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.fragmenttwo_view.chat.ui.ConversationListFragment


class layout_chat_one : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_chat_one_view, null)

    }

    private var conversationListFragment: ConversationListFragment? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversationListFragment = ConversationListFragment()
        conversationListFragment!!.setConversationListItemClickListener { conversation ->
            startActivity(
                Intent(activity, ChatActivity::class.java).putExtra(
                    EaseConstant.EXTRA_USER_ID, conversation.conversationId()
                )
            )
        }
        EMClient.getInstance().login("18267913849", "123", object : EMCallBack {
            override fun onSuccess() {
                println("chat登录成")
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // add and show first fragment
                activity?.getSupportFragmentManager()?.beginTransaction()?.add(
                    R.id.fragment_container,
                    conversationListFragment!!
                )?.commit()

            }

            override fun onProgress(p0: Int, p1: String?) {

            }

            override fun onError(p0: Int, p1: String?) {
                println("chat登录失败" + p0 + p1)
            }

        })


    }

}