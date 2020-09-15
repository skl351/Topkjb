package com.top.kjb.tabfragment.fragmenttwo_view.chat;


import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.top.kjb.R;
import com.top.kjb.originpack.BaseActivity;

public class ChatActivity extends BaseActivity {

    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chatactivity_view);
        chatFragment = new EaseChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}
