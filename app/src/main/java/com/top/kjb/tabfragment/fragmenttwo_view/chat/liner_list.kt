package com.top.kjb.tabfragment.fragmenttwo_view.chat

import android.os.Bundle
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.domain.EaseUser
import com.hyphenate.easeui.ui.EaseContactListFragment
import com.hyphenate.easeui.utils.EaseCommonUtils
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_liner_list_view.*
import java.util.*

class liner_list : BaseActivity(), View.OnClickListener {
    private var contactListFragment: EaseContactListFragment? = null
    var usernames: List<String>? = null
    var userlist: HashMap<String, EaseUser> = HashMap<String, EaseUser>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_liner_list_view)
        init_click()
        usernames = EMClient.getInstance().contactManager().allContactsFromServer
        for (username in (usernames as MutableList<String>?)!!) {
            val user = EaseUser(username)
            EaseCommonUtils.setUserInitialLetter(user)
            userlist.put(username, user)
        }
        contactListFragment = EaseContactListFragment()
        contactListFragment?.setContactsMap(userlist)
        getSupportFragmentManager().beginTransaction().add(
            R.id.fragment_container,
            contactListFragment!!
        ).commit()
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }
}